package kr.dklog.service;

import kr.dklog.common.exception.AlreadyAuthStudentException;
import kr.dklog.common.exception.NotMatchAuthCodeException;
import kr.dklog.common.exception.StudentNotFoundException;
import kr.dklog.common.util.NcpSmsUtil;
import kr.dklog.dto.SmsSendResponseDto;
import kr.dklog.dto.StudentDto;
import kr.dklog.dto.request.RequestAuthCodeDto;
import kr.dklog.dto.response.ResponseAuthResultDto;
import kr.dklog.mapper.SmsSendResponseMapper;
import kr.dklog.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthCodeService {

    private final StudentMapper studentMapper;

    private final NcpSmsUtil ncpSmsUtil;

    private final HttpSession httpSession;

    private final SmsSendResponseMapper smsSendResponseMapper;

    public ResponseAuthResultDto sendAuthCode(RequestAuthCodeDto requestDto) {

        studentMapper.findByName(requestDto.getName())
                .orElseThrow(StudentNotFoundException::new);
        StudentDto studentDto = studentMapper.findByPhoneNumber(requestDto.getPhoneNumber())
                .orElseThrow(StudentNotFoundException::new);

        if (studentDto.getAuthStatus().equals("Y")) {
            throw new AlreadyAuthStudentException();
        }

        studentDto.setAuthCode(getAuthCode());
        studentMapper.updateStudent(studentDto);

        ResponseAuthResultDto responseDto = null;
        try {
            Map<String, Object> response = ncpSmsUtil.sendSms(studentDto);

            SmsSendResponseDto smsSendResponseDto = new SmsSendResponseDto();
            smsSendResponseDto.setRequestId((String) response.get("requestId"));
            smsSendResponseDto.setRequestTime((String) response.get("requestTime"));
            smsSendResponseDto.setStatusCode((String) response.get("statusCode"));
            smsSendResponseDto.setStatusName((String) response.get("statusName"));

            smsSendResponseMapper.save(smsSendResponseDto);

            if (response.get("statusCode").equals("202")) {
                responseDto = new ResponseAuthResultDto(studentDto.getStudentId(), "인증번호를 전송했습니다.");
            } else {
                responseDto = new ResponseAuthResultDto(studentDto.getStudentId(), "인증번호 전송을 실패했습니다.");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return responseDto;
    }

    public void validate(StudentDto requestDto) {
        StudentDto studentDto = studentMapper.findById(requestDto.getStudentId())
                .orElseThrow(StudentNotFoundException::new);

        String requestCode = requestDto.getAuthCode();
        String originalCode = studentDto.getAuthCode();
        if (!requestCode.equals(originalCode)) {
            throw new NotMatchAuthCodeException();
        }

        studentDto.setAuthStatus("Y");
        studentMapper.updateStudent(studentDto);

        httpSession.setAttribute("studentDto", studentDto);
    }

    private String getAuthCode() {
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());

        int len = 5;
        String result = "";
        result += Integer.toString(generator.nextInt(8) + 1);
        for (int i = 0; i < len; i++) {
            result += Integer.toString(generator.nextInt(9));
        }

        return result;
    }
}
