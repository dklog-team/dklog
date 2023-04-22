package kr.dklog.mapper;

import kr.dklog.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface TestMapper {

    Optional<TestDto> findById(Long id);
}
