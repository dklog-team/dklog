package kr.dklog.service;

import kr.dklog.dto.VisitorDto;
import kr.dklog.mapper.VisitorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorMapper visitorMapper;

    public VisitorDto insert(VisitorDto visitorDto) {
        visitorMapper.save(visitorDto);

        return visitorDto;
    }
}
