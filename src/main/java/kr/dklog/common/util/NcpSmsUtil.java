package kr.dklog.common.util;

import kr.dklog.dto.StudentDto;
import kr.dklog.dto.common.RequestSmsDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;

@Component
public class NcpSmsUtil {

    @Value("${sms.type}")
    private String type;

    @Value("${sms.from}")
    private String from;

    @Value("${ncp.access-key-id}")
    private String ncpAccessKey;

    @Value("${ncp.secret-key}")
    private String ncpSecretKey;

    @Value("${ncp.notification.service-id}")
    private String ncpServiceId;

    private String ncpApiUrl = "https://sens.apigw.ntruss.com";

    public Map<String, Object> sendSms(StudentDto studentDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String content = "[dk log] 인증번호: " + studentDto.getAuthCode();
        RequestSmsDto requestSmsDto = RequestSmsDto.builder()
                .type(type)
                .from(from)
                .content(content)
                .messages(Collections.singletonList(RequestSmsDto.Message.builder()
                        .to(studentDto.getPhoneNumber())
                        .build()))
                .build();

        Map<String, Object> response = send(requestSmsDto, new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    private Map<String, Object> send(RequestSmsDto requestSmsDto, long timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        WebClient webClient = WebClient.builder()
                .baseUrl(ncpApiUrl)
                .defaultHeader("x-ncp-apigw-timestamp", String.valueOf(timestamp))
                .defaultHeader("x-ncp-iam-access-key", ncpAccessKey)
                .defaultHeader("x-ncp-apigw-signature-v2", makeSignature(String.valueOf(timestamp), "/sms/v2/services/" + ncpServiceId + "/messages", HttpMethod.POST))
                .build();

        Map<String, Object> response = webClient.post()
                .uri("/sms/v2/services/" + ncpServiceId + "/messages")
                .bodyValue(requestSmsDto)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return response;
    }

    private String makeSignature(String timestampString, String urlString, HttpMethod httpMethod) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        String space = " ";
        String newLine = "\n";
        String method = httpMethod.name();
        String accessKey = ncpAccessKey;
        String secretKey = ncpSecretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(urlString)
                .append(newLine)
                .append(timestampString)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));

        return Base64.encodeBase64String(rawHmac);
    }
}
