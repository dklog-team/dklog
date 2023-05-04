package kr.dklog.controller;

import kr.dklog.common.session.LoginMember;
import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.request.RequestAddCommentDto;
import kr.dklog.dto.request.RequestDeleteCommentDto;
import kr.dklog.dto.request.RequestUpdateCommentDto;
import kr.dklog.dto.response.ResponseCommentDto;
import kr.dklog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<List<ResponseCommentDto>> addComment(@RequestBody RequestAddCommentDto requestDto) {
        List<ResponseCommentDto> responseCommentDtoList = commentService.add(requestDto);
        return ResponseEntity.ok(responseCommentDtoList);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<List<ResponseCommentDto>> removeComment(@PathVariable Long commentId, @RequestParam Long postId, @RequestParam Long memberId, @LoginMember SessionMember sessionMember) {
        RequestDeleteCommentDto requestDto = new RequestDeleteCommentDto(postId, commentId, memberId);
        List<ResponseCommentDto> responseCommentDtoList = commentService.delete(requestDto, sessionMember);
        return ResponseEntity.ok(responseCommentDtoList);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(@PathVariable Long commentId, @RequestBody RequestUpdateCommentDto requestDto, @LoginMember SessionMember sessionMember) {
        requestDto.setCommentId(commentId);
        ResponseCommentDto responseCommentDto = commentService.update(requestDto, sessionMember);
        return ResponseEntity.ok(responseCommentDto);
    }
}
