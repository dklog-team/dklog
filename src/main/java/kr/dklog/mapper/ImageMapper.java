package kr.dklog.mapper;

import kr.dklog.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    int insertImage(ImageDto imageDto);
}
