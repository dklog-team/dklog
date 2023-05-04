package kr.dklog.controller;

import kr.dklog.common.session.LoginMember;
import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.request.RequestPostDto;
import kr.dklog.dto.request.RequestUpdatePostDto;
import kr.dklog.dto.response.ResponseCommentDto;
import kr.dklog.dto.response.ResponsePostDto;
import kr.dklog.service.CommentService;
import kr.dklog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    @GetMapping("/write")
    public String postWritePage(@LoginMember SessionMember member) {
        if (member != null) {
            return "view/write";
        }
        return "redirect:/";
    }

    @GetMapping("/{postId}")
    public String postDetail(Model model, @PathVariable Long postId, @LoginMember SessionMember sessionMember) {
        if (sessionMember != null) {
            model.addAttribute("member", sessionMember);
        }

        ResponsePostDto responsePostDto = postService.get(postId);
        List<ResponseCommentDto> responseCommentDtoList = commentService.getList(postId);

        model.addAttribute("responsePostDto", responsePostDto);
        model.addAttribute("responseCommentDtoList", responseCommentDtoList);

        return "view/post-detail";
    }

    @PostMapping("/upload")
    public String postWrite(RequestPostDto dto, @LoginMember SessionMember member) {
        if (member != null) {
            dto.setMemberId(member.getMemberId());
            boolean result = postService.write(dto);
            Long postId = dto.getPostId();
            if (result) {
                return "redirect:/post/" + postId;
            }
        }

        return "view/error/post-upload-fail";
    }

    @GetMapping("/edit/{postId}")
    public String postEditPage(Model model, @PathVariable Long postId, @LoginMember SessionMember sessionMember) {
        if (sessionMember != null) {
            ResponsePostDto responsePostDto = postService.get(postId);
            if (sessionMember.getMemberId() == responsePostDto.getMemberId()) {
                model.addAttribute("responsePostDto", responsePostDto);
                return "view/write";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String postUpdate(RequestUpdatePostDto requestUpdatePostDto) {
        if (postService.modify(requestUpdatePostDto)) {
            long postId = requestUpdatePostDto.getPostId();
            return "redirect:/post/" + postId;
        }

        return "view/error/post-upload-fail";
    }

    @GetMapping("/delete/{postId}")
    public String postDelete(@PathVariable("postId") Long postId, @LoginMember SessionMember member) throws Exception {
        ResponsePostDto responsePostDto = postService.get(postId);

        if (member != null) {
            if (member.getMemberId() == responsePostDto.getMemberId()) {
                postService.delete(postId);
            }

        }
        return "redirect:/";
    }
}
