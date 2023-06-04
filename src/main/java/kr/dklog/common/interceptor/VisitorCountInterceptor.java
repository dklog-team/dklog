package kr.dklog.common.interceptor;

import kr.dklog.dto.VisitorDto;
import kr.dklog.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class VisitorCountInterceptor implements HandlerInterceptor {

    private final VisitorService visitorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Boolean visitFlag = (Boolean) session.getAttribute("visitFlag");
        if (visitFlag == null) {
            log.info("======================================================================================");
            session.setAttribute("visitFlag", true);
            log.info("client ip: {}", getRemoteIP(request));
            log.info("client os: {}", getOs(request.getHeader("User-Agent")));
            log.info("request uri: {}", request.getRequestURL());

            VisitorDto visitorDto = new VisitorDto();
            visitorDto.setIp(getRemoteIP(request));
            visitorDto.setOs(getOs(request.getHeader("User-Agent")));
            visitorDto.setRequestUrl(String.valueOf(request.getRequestURL()));
            visitorDto.setVisitedDate(LocalDateTime.now());

            VisitorDto savedVisitor = visitorService.insert(visitorDto);
            log.info("saved visitor: {}", savedVisitor.toString());
            log.info("======================================================================================");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    private String getOs(String userAgent) {
        String os = "Unknown";
        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("windows")) {
            os = "Windows";
        } else if(userAgent.toLowerCase().contains("macintosh")) {
            os = "Mac";
        } else if(userAgent.toLowerCase().contains("x11")) {
            os = "Unix";
        } else if(userAgent.toLowerCase().contains("android")) {
            os = "Android";
        } else if(userAgent.toLowerCase().contains("iphone")) {
            os = "iOS";
        }

        return os;
    }
}
