package kr.dklog.mapper;

import kr.dklog.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<MemberDto> findByGithubUsername(String githubUsername);

    void updateMember(MemberDto memberDto);

    int save(MemberDto memberDto);

    Optional<MemberDto> findById(Long memberId);
}
