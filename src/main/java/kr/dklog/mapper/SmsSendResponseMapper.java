package kr.dklog.mapper;

import kr.dklog.dto.SmsSendResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsSendResponseMapper {

    int save(SmsSendResponseDto smsSendResponseDto);
}
