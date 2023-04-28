package kr.dklog.controller;

import kr.dklog.dto.StudentDto;
import kr.dklog.dto.request.RequestAuthCodeDto;
import kr.dklog.dto.response.ResponseAuthResultDto;
import kr.dklog.service.AuthCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final AuthCodeService authCodeService;

    @GetMapping("/auth")
    public String studentAuth() {
        return "view/student-auth";
    }

    @GetMapping("/auth/code")
    @ResponseBody
    public ResponseEntity<?> getAuthCode(@ModelAttribute RequestAuthCodeDto requestDto) {
        ResponseAuthResultDto responseDto = authCodeService.sendAuthCode(requestDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @ResponseBody
    @GetMapping("/auth/code/submit")
    public ResponseEntity<?> submit(@ModelAttribute StudentDto requestDto) {
        authCodeService.validate(requestDto);

        return ResponseEntity.ok().build();
    }
}
