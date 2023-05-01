package kr.dklog.mapper;

import kr.dklog.dto.ImageDto;
import kr.dklog.dto.response.ResponseUploadResultDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    int save(ResponseUploadResultDto responseDto);

}
