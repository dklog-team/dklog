package kr.dklog.mapper;

import kr.dklog.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(CommentDto commentDto);
    List<CommentDto> selectComment(Long post_id);
}
