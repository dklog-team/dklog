package kr.dklog.mapper;

import kr.dklog.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentDto> selectComment(Long post_id);
    void insertComment(CommentDto commentDto);
    void updateComment(CommentDto commentDto);
    void deleteComment(CommentDto commentDto);
    Long insertedCommentId();
}
