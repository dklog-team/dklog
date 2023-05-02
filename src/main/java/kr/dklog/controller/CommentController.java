package kr.dklog.controller;

import kr.dklog.common.session.LoginMember;
import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.CommentDto;
import kr.dklog.dto.response.ResponseCommentDto;
import kr.dklog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/detail/comment/{postId}")
    public String commentPage(Model model, @PathVariable Long postId) {
        List<ResponseCommentDto> commentList = commentService.getComment(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("postId", postId);
        return "/view/comment";
    }

    @PostMapping("/insert/comment")
    @ResponseBody
    public ResponseCommentDto addComment(@RequestBody CommentDto commentDto, @LoginMember SessionMember sessionMember){
        LocalDateTime now = LocalDateTime.now();
        CommentDto comment = new CommentDto();
        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        if(commentDto.getContent() != null && commentDto.getContent().trim() != ""){
            comment.setContent(commentDto.getContent());
            comment.setCreatedDate(now);
            comment.setPostId(commentDto.getPostId());
            comment.setMemberId(sessionMember.getMemberId());
            commentService.addComment(comment);

            responseCommentDto.setContent(commentDto.getContent());
            responseCommentDto.setWriteDate("방금전");
            responseCommentDto.setUsername(sessionMember.getUsername());
            return responseCommentDto;
        }
        return responseCommentDto;
    }

    @PostMapping("/comment/update")
    @ResponseBody
    public ResponseCommentDto fixComment(@RequestBody CommentDto commentDto){
//        , @LoginMember SessionMember sessionMember
        LocalDateTime now = LocalDateTime.now();
        CommentDto fixedComment = new CommentDto();
        fixedComment.setContent(commentDto.getContent());
        fixedComment.setModifiedDate(now);
        fixedComment.setCommentId(commentDto.getCommentId());
        fixedComment.setMemberId(1L);
        commentService.fixComment(fixedComment);

        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        responseCommentDto.setContent(commentDto.getContent());
        responseCommentDto.setWriteDate(now.format(DateTimeFormatter.ofPattern("수정됨 방금전")));
        return responseCommentDto;
    }

    @PostMapping("/comment/delete")
    @ResponseBody
    public void removeComment(@RequestBody CommentDto commentDto, @LoginMember SessionMember sessionMember){
        CommentDto removedComment = new CommentDto();
        removedComment.setCommentId(commentDto.getCommentId());
        removedComment.setMemberId(sessionMember.getMemberId());
        commentService.removeComment(removedComment);
    }
}
