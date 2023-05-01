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

    //comment insert하는 컨트롤러
    @PostMapping("/insertComment")
    @ResponseBody
    public ResponseCommentDto addComment(@RequestBody CommentDto commentDto){
        LocalDateTime created_date = LocalDateTime.now();
        CommentDto Comment = new CommentDto(commentDto.getContent(), created_date, commentDto.getPostId());
        ResponseCommentDto responseCommentDto = new ResponseCommentDto(commentDto.getContent(), created_date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")), commentDto.getPostId());
        commentService.addComment(Comment);
        return responseCommentDto;
    }
}
