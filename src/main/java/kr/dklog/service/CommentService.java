package kr.dklog.service;

import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.CommentDto;
import kr.dklog.dto.request.RequestAddCommentDto;
import kr.dklog.dto.request.RequestDeleteCommentDto;
import kr.dklog.dto.request.RequestUpdateCommentDto;
import kr.dklog.dto.response.ResponseCommentDto;
import kr.dklog.mapper.CommentMapper;
import kr.dklog.mapper.MemberMapper;
import kr.dklog.mapper.PostMapper;
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

    private final PostMapper postMapper;

    public List<ResponseCommentDto> getComment(Long postId) {
        LocalDateTime now = LocalDateTime.now();
        List<CommentDto> commentDtos = commentMapper.selectComment(postId);
        List<ResponseCommentDto> responseCommentDtoList = commentDtos.stream().map(commentDto -> {
            ResponseCommentDto responseCommentDto = new ResponseCommentDto();
            Period period = Period.between(commentDto.getCreatedDate().toLocalDate(), now.toLocalDate());
            Duration duration = Duration.between(commentDto.getCreatedDate(), now);
            String lastWriteDate = "";
            if (commentDto.getModifiedDate() != null) {
                period = Period.between(commentDto.getModifiedDate().toLocalDate(), now.toLocalDate());
                duration = Duration.between(commentDto.getModifiedDate(), now);
                lastWriteDate = "수정됨 ";
            }
            if (period.getYears() > 0) {
                lastWriteDate += period.getYears() + "년전";
            } else if (period.getMonths() > 0) {
                lastWriteDate += period.getMonths() + "개월전";
            } else if (period.getDays() > 0) {
                lastWriteDate += period.getDays() + "일전";
            } else if (duration.toHours() > 0) {
                lastWriteDate += duration.toHours() + "시간전";
            } else if (duration.toMinutes() > 0) {
                lastWriteDate += duration.toMinutes() + "분전";
            } else {
                lastWriteDate += "방금전";
            }
            responseCommentDto.setContent(commentDto.getContent());
            responseCommentDto.setCommentId(commentDto.getCommentId());
            responseCommentDto.setPostId(commentDto.getPostId());
            responseCommentDto.setMemberId(commentDto.getMemberId());
            responseCommentDto.setUsername(memberMapper.findUsernameById(commentDto.getMemberId()));
            responseCommentDto.setWriteDate(lastWriteDate);
            return responseCommentDto;
        }).collect(Collectors.toList());
        return responseCommentDtoList;
    }

    public void addComment(CommentDto commentDto) {
        commentMapper.insertComment(commentDto);
    }

    public void fixComment(CommentDto commentDto) {
        commentMapper.updateComment(commentDto);
    }

    public void removeComment(CommentDto commentDto) {
        commentMapper.deleteComment(commentDto);
    }

    public Long getInsertedCommentId() {
        return commentMapper.insertedCommentId();
    }

    public List<ResponseCommentDto> getList(Long postId) {
        LocalDateTime now = LocalDateTime.now();
        List<CommentDto> commentDtoList = commentMapper.findAllByPostId(postId);

        List<ResponseCommentDto> responseCommentDtoList = commentDtoList.stream().map(commentDto -> {
            ResponseCommentDto responseCommentDto = new ResponseCommentDto();
            Period period = Period.between(commentDto.getCreatedDate().toLocalDate(), now.toLocalDate());
            Duration duration = Duration.between(commentDto.getCreatedDate(), now);
            String lastWriteDate = "";
            if (commentDto.getModifiedDate() != null) {
                period = Period.between(commentDto.getModifiedDate().toLocalDate(), now.toLocalDate());
                duration = Duration.between(commentDto.getModifiedDate(), now);
                lastWriteDate = "수정됨 ";
            }
            if (period.getYears() > 0) {
                lastWriteDate += period.getYears() + "년전";
            } else if (period.getMonths() > 0) {
                lastWriteDate += period.getMonths() + "개월전";
            } else if (period.getDays() > 0) {
                lastWriteDate += period.getDays() + "일전";
            } else if (duration.toHours() > 0) {
                lastWriteDate += duration.toHours() + "시간전";
            } else if (duration.toMinutes() > 0) {
                lastWriteDate += duration.toMinutes() + "분전";
            } else {
                lastWriteDate += "방금전";
            }
            responseCommentDto.setContent(commentDto.getContent());
            responseCommentDto.setCommentId(commentDto.getCommentId());
            responseCommentDto.setPostId(commentDto.getPostId());
            responseCommentDto.setMemberId(commentDto.getMemberId());
            responseCommentDto.setUsername(commentDto.getUsername());
            responseCommentDto.setPicture(commentDto.getPicture());
            responseCommentDto.setWriteDate(lastWriteDate);
            return responseCommentDto;
        }).collect(Collectors.toList());

        return responseCommentDtoList;
    }

    public List<ResponseCommentDto> add(RequestAddCommentDto requestDto) {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(requestDto.getContent());
        commentDto.setCreatedDate(LocalDateTime.now());
        commentDto.setMemberId(requestDto.getMemberId());
        commentDto.setPostId(requestDto.getPostId());

        commentMapper.save(commentDto);

        List<ResponseCommentDto> responseCommentDtoList = getList(requestDto.getPostId());

        return responseCommentDtoList;
    }

    public List<ResponseCommentDto> delete(RequestDeleteCommentDto requestDto, SessionMember sessionMember) {
        postMapper.findById(requestDto.getPostId())
                .orElseThrow(RuntimeException::new);

        commentMapper.findById(requestDto.getCommentId())
                .orElseThrow(RuntimeException::new);

        memberMapper.findById(requestDto.getMemberId())
                .orElseThrow(RuntimeException::new);

        if (!sessionMember.getMemberId().equals(requestDto.getMemberId())) {
            throw new RuntimeException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentMapper.deleteById(requestDto.getCommentId());
        return getList(requestDto.getPostId());
    }

    public ResponseCommentDto update(RequestUpdateCommentDto requestDto, SessionMember sessionMember) {
        postMapper.findById(requestDto.getPostId())
                .orElseThrow(RuntimeException::new);

        commentMapper.findById(requestDto.getCommentId())
                .orElseThrow(RuntimeException::new);

        memberMapper.findById(requestDto.getMemberId())
                .orElseThrow(RuntimeException::new);

        if (!sessionMember.getMemberId().equals(requestDto.getMemberId())) {
            throw new RuntimeException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        requestDto.setModifiedDate(LocalDateTime.now());

        commentMapper.updateById(requestDto);

        CommentDto commentDto = commentMapper.findById(requestDto.getCommentId())
                .orElseThrow(RuntimeException::new);

        LocalDateTime now = LocalDateTime.now();
        ResponseCommentDto responseCommentDto = new ResponseCommentDto();
        Period period = Period.between(commentDto.getCreatedDate().toLocalDate(), now.toLocalDate());
        Duration duration = Duration.between(commentDto.getCreatedDate(), now);
        String lastWriteDate = "";
        if (commentDto.getModifiedDate() != null) {
            period = Period.between(commentDto.getModifiedDate().toLocalDate(), now.toLocalDate());
            duration = Duration.between(commentDto.getModifiedDate(), now);
            lastWriteDate = "수정됨 ";
        }
        if (period.getYears() > 0) {
            lastWriteDate += period.getYears() + "년전";
        } else if (period.getMonths() > 0) {
            lastWriteDate += period.getMonths() + "개월전";
        } else if (period.getDays() > 0) {
            lastWriteDate += period.getDays() + "일전";
        } else if (duration.toHours() > 0) {
            lastWriteDate += duration.toHours() + "시간전";
        } else if (duration.toMinutes() > 0) {
            lastWriteDate += duration.toMinutes() + "분전";
        } else {
            lastWriteDate += "방금전";
        }
        responseCommentDto.setContent(commentDto.getContent());
        responseCommentDto.setCommentId(commentDto.getCommentId());
        responseCommentDto.setPostId(commentDto.getPostId());
        responseCommentDto.setMemberId(commentDto.getMemberId());
        responseCommentDto.setUsername(commentDto.getUsername());
        responseCommentDto.setPicture(commentDto.getPicture());
        responseCommentDto.setWriteDate(lastWriteDate);
        return responseCommentDto;
    }

    public int count(Long postId) {
        return commentMapper.countBy(postId);
    }
}
