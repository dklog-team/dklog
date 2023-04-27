package kr.dklog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentController {
    @GetMapping("/comment")
    public String commentController() {
        return "view/comment";
    }
}
