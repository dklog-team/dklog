package kr.dklog.mapper;

import kr.dklog.dto.VisitorDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisitorMapper {

    int save(VisitorDto visitorDto);
}
