package kr.dklog.service;

import kr.dklog.dto.CommentDto;
import kr.dklog.dto.response.ResponseCommentDto;
import kr.dklog.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    public void addComment(CommentDto commentDto){
        commentMapper.insertComment(commentDto);
    }
    public List<ResponseCommentDto> getComment(Long postId){
        List<CommentDto> commentDtos = commentMapper.selectComment(postId);

        List<ResponseCommentDto> responseCommentDtoList = commentDtos.stream().map(commentDto -> {
            ResponseCommentDto responseCommentDto = new ResponseCommentDto();
            responseCommentDto.setContent(commentDto.getContent());
            responseCommentDto.setCreatedDate(commentDto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")));
            responseCommentDto.setPostId(commentDto.getPostId());
            return responseCommentDto;
        }).collect(Collectors.toList());

        return responseCommentDtoList;
    }
}
