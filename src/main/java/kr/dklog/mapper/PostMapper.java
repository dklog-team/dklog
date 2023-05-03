package kr.dklog.mapper;

import kr.dklog.dto.PostDto;
import kr.dklog.dto.common.RequestListDto;
import kr.dklog.dto.request.RequestPostDto;
import kr.dklog.dto.request.RequestUpdatePostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    Optional<PostDto> findById(Long postId);

    List<PostDto> findAll(RequestListDto requestListDto);

    Long countBy(String keyword);

    List<PostDto> findAll();

   int save(RequestPostDto requestPostDto);

    int update(RequestUpdatePostDto requestUpdatePostDto);

    int delete(Long postId);
}
