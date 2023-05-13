package kr.dklog.controller;

import kr.dklog.common.session.LoginMember;
import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.request.RequestAddCommentDto;
import kr.dklog.dto.request.RequestDeleteCommentDto;
import kr.dklog.dto.request.RequestUpdateCommentDto;
import kr.dklog.dto.response.ResponseCommentDto;
import kr.dklog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<List<ResponseCommentDto>> addComment(@RequestBody RequestAddCommentDto requestDto, HttpServletRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        requestLog(request, requestDto.toString());
        List<ResponseCommentDto> responseCommentDtoList = commentService.add(requestDto);

        String data = "";
        for (ResponseCommentDto responseCommentDto : responseCommentDtoList) {
            data += responseCommentDto.toString();
        }
        ResponseEntity<List<ResponseCommentDto>> response = ResponseEntity.ok(responseCommentDtoList);
        stopWatch.stop();
        responseLog(response, data, stopWatch.getTotalTimeMillis());
        return response;
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<List<ResponseCommentDto>> removeComment(@PathVariable Long commentId, @RequestParam Long postId, @RequestParam Long memberId, @LoginMember SessionMember sessionMember, HttpServletRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        RequestDeleteCommentDto requestDto = new RequestDeleteCommentDto(postId, commentId, memberId);
        requestLog(request, requestDto.toString());
        List<ResponseCommentDto> responseCommentDtoList = commentService.delete(requestDto, sessionMember);
        String data = "";
        for (ResponseCommentDto responseCommentDto : responseCommentDtoList) {
            data += responseCommentDto.toString();
        }
        ResponseEntity<List<ResponseCommentDto>> response = ResponseEntity.ok(responseCommentDtoList);
        stopWatch.stop();
        responseLog(response, data, stopWatch.getTotalTimeMillis());
        return response;
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(@PathVariable Long commentId, @RequestBody RequestUpdateCommentDto requestDto, @LoginMember SessionMember sessionMember, HttpServletRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        requestDto.setCommentId(commentId);
        requestLog(request, requestDto.toString());
        ResponseCommentDto responseCommentDto = commentService.update(requestDto, sessionMember);
        ResponseEntity<ResponseCommentDto> response = ResponseEntity.ok(responseCommentDto);
        stopWatch.stop();
        responseLog(response, responseCommentDto.toString(), stopWatch.getTotalTimeMillis());
        return response;
    }

    private void requestLog(HttpServletRequest request, String dataLog) {
        log.info("======================================================================================");
        log.info("Request [ {} ]", LocalDateTime.now());
        log.info("======================================================================================");
        log.info("method: {}", request.getMethod());
        log.info("request url: {}", request.getRequestURL());
        log.info("request data: {}", dataLog);
        log.info("ip: {}", getRemoteIP(request));
        log.info("user agent: {}", request.getHeader("User-Agent"));
        log.info("======================================================================================");
    }

    private void responseLog(ResponseEntity response, String dataLog, long totalTimeMillis) {
        log.info("======================================================================================");
        log.info("Response [ {} ]", LocalDateTime.now());
        log.info("======================================================================================");
        log.info("process time: {}", totalTimeMillis);
        log.info("response status: {}", response.getStatusCode());
        log.info("response data: {}", dataLog);
        log.info("======================================================================================");
    }

    private String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");

        //proxy 환경일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        //웹로직 서버일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr() ;
        }

        return ip;
    }
}
