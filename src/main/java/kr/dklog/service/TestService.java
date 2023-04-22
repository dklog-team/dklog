package kr.dklog.service;

import kr.dklog.dto.TestDto;
import kr.dklog.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestMapper testMapper;

    public TestDto get(Long id) {
        TestDto testDto = testMapper.findById(id)
                .orElseThrow(RuntimeException::new);

        return testDto;
    }
}
