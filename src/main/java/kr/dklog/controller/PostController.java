package kr.dklog.controller;

import kr.dklog.common.session.LoginMember;
import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.request.RequestPostDto;
import kr.dklog.dto.request.RequestUpdatePostDto;
import kr.dklog.service.PostService;
import lombok.RequiredArgsConstructor;
import kr.dklog.dto.response.ResponsePostDto;
import kr.dklog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/write")
    public String postWritePage() {
        return "view/write";
    }

    @GetMapping("/{postId}")
    public String postDetail(Model model, @PathVariable Long postId, @LoginMember SessionMember member) {
        ResponsePostDto responsePostDto = postService.get(postId);
        if (member != null) {
            if (member.getMemberId() == responsePostDto.getMemberId()) {
                model.addAttribute("member", member);
            }
        }
        model.addAttribute("responsePostDto", responsePostDto);

        return "view/post-detail";
    }

    @PostMapping("/upload")
    public String postWrite(RequestPostDto dto, @LoginMember SessionMember member) {
        if (member != null) {
            dto.setMemberId(member.getMemberId());
            System.out.println(dto.getMemberId());
            boolean result = postService.write(dto);
            Long postId = dto.getPostId();
            if (result) {
                return "redirect:/post/" + postId;
            }
        }

        return "view/error/post-upload-fail";
    }

    @GetMapping("/edit/{postId}")
    public String postEditPage(Model model, @PathVariable Long postId) {
        ResponsePostDto responsePostDto = postService.get(postId);

        model.addAttribute("responsePostDto", responsePostDto);

        return "view/write";
    }

    @PostMapping("/update")
    public String postUpdate(RequestUpdatePostDto requestUpdatePostDto) {
        if (postService.modify(requestUpdatePostDto)) {
            long postId = requestUpdatePostDto.getPostId();
            return "redirect:/post/" + postId;
        }

        return "view/error/post-upload-fail";
    }
}
