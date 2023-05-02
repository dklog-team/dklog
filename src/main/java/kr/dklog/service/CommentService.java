package kr.dklog.service;

import kr.dklog.dto.CommentDto;
import kr.dklog.dto.response.ResponseCommentDto;
import kr.dklog.mapper.CommentMapper;
import kr.dklog.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    private final MemberMapper memberMapper;

    public List<ResponseCommentDto> getComment(Long postId){
        LocalDateTime now = LocalDateTime.now();
        List<CommentDto> commentDtos = commentMapper.selectComment(postId);
        List<ResponseCommentDto> responseCommentDtoList = commentDtos.stream().map(commentDto -> {
            ResponseCommentDto responseCommentDto = new ResponseCommentDto();
            Period period = Period.between(commentDto.getCreatedDate().toLocalDate(),now.toLocalDate());
            Duration duration = Duration.between(commentDto.getCreatedDate(),now);
            String lastWriteDate = "";
            if(commentDto.getModifiedDate() != null){
                period = Period.between(commentDto.getModifiedDate().toLocalDate(),now.toLocalDate());
                duration = Duration.between(commentDto.getModifiedDate(),now);
                lastWriteDate = "수정됨 ";
            }
            if(period.getYears()>0) {
                lastWriteDate += String.valueOf(period.getYears()) + "년전";
            }else if(period.getMonths()>0){
                lastWriteDate += String.valueOf(period.getMonths()) + "개월전";
            }else if(period.getDays()>0){
                lastWriteDate += String.valueOf(period.getDays()) + "일전";
            }else if(duration.toHours()>0) {
                lastWriteDate += String.valueOf(duration.toHours()) + "시간전";
            }else if(duration.toMinutes()>0){
                lastWriteDate += String.valueOf(duration.toMinutes()) + "분전";
            }else{
                lastWriteDate += "방금전";
            }
            responseCommentDto.setContent(commentDto.getContent());
            responseCommentDto.setCommendID(commentDto.getCommentId());
            responseCommentDto.setPostId(commentDto.getPostId());
            responseCommentDto.setMemberId(commentDto.getMemberId());
            responseCommentDto.setUsername(memberMapper.findUsernameById(commentDto.getMemberId()));
            responseCommentDto.setWriteDate(lastWriteDate);
            return responseCommentDto;
        }).collect(Collectors.toList());
        return responseCommentDtoList;
    }

    public void addComment(CommentDto commentDto){
        commentMapper.insertComment(commentDto);
    }

    public void fixComment(CommentDto commentDto){
        commentMapper.updateComment(commentDto);
    }

    public void removeComment(CommentDto commentDto){
        commentMapper.deleteComment(commentDto);
    }
}
