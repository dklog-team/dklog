package kr.dklog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/auth")
    public String studentAuth() {
        return "view/student-auth";
    }
}
