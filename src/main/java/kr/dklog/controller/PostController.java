package kr.dklog.controller;

import kr.dklog.common.session.LoginMember;
import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.request.RequestPostDto;
import kr.dklog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/write")
    public String postWritePage() {
        return "view/write";
    }

    @GetMapping("/upload")
    public String postWrite(RequestPostDto dto, @LoginMember SessionMember member) {
        if (member != null) {
            dto.setMemberId(member.getMemberId());
            System.out.println(dto.getMemberId());
            boolean result = postService.write(dto);
            Long postId = dto.getPostId();
            if(result){
                return "redirect:/post/" + postId;
            }
        }

        return "view/error/post-upload-fail";
    }
}
