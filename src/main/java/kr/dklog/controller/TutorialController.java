package kr.dklog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TutorialController {
    @GetMapping("/tutorial")
    public String tutorialPage(){
        return "view/write-tutorial";
    }
}
