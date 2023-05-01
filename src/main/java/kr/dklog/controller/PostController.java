package kr.dklog.controller;

import kr.dklog.dto.response.ResponsePostDto;
import kr.dklog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/write")
    public String getPostWrite() {
        return "view/write";
    }

    @GetMapping("/{postId}")
    public String postDetail(Model model, @PathVariable Long postId) {
        ResponsePostDto responsePostDto = postService.get(postId);

        model.addAttribute("responsePostDto", responsePostDto);

        return "view/post-detail";
    }
}
