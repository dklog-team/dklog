package kr.dklog.mapper;

import kr.dklog.dto.CommentDto;
import kr.dklog.dto.request.RequestUpdateCommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    List<CommentDto> selectComment(Long post_id);

    void insertComment(CommentDto commentDto);

    void updateComment(CommentDto commentDto);

    void deleteComment(CommentDto commentDto);

    Long insertedCommentId();

    List<CommentDto> findAllByPostId(Long postId);

    int save(CommentDto requestDto);

    Optional<CommentDto> findById(Long commentId);

    void deleteById(Long commentId);

    void updateById(RequestUpdateCommentDto commentId);
}
