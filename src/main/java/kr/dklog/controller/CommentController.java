package kr.dklog.controller;

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
    public ResponseCommentDto addComment(@RequestBody CommentDto commentDto){
        LocalDateTime now = LocalDateTime.now();
        CommentDto comment = new CommentDto();
        comment.setContent(commentDto.getContent());
        comment.setCreatedDate(now);
        comment.setPostId(commentDto.getPostId());
        commentService.addComment(comment);

        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        responseCommentDto.setContent(commentDto.getContent());
        responseCommentDto.setCreatedDate(now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")));
        responseCommentDto.setPostId(commentDto.getPostId());
        return responseCommentDto;
    }

    @PostMapping("/update/comment")
    @ResponseBody
    public ResponseCommentDto fixComment(@RequestBody CommentDto commentDto){
        LocalDateTime now = LocalDateTime.now();
        CommentDto fixedComment = new CommentDto();
        fixedComment.setContent(commentDto.getContent());
        fixedComment.setModifiedDate(now);
        fixedComment.setCommentID(commentDto.getCommentID());
        commentService.fixComment(fixedComment);

        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        responseCommentDto.setContent(commentDto.getContent());
        responseCommentDto.setModifiedDate(now.format(DateTimeFormatter.ofPattern("수정됨: yyyy년 MM월 dd일 HH:mm")));
        responseCommentDto.setCommendID(commentDto.getCommentID());
        return responseCommentDto;
    }

}
