package kr.dklog.service;

import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.MemberDto;
import kr.dklog.dto.StudentDto;
import kr.dklog.dto.common.OAuthAttributesDto;
import kr.dklog.mapper.MemberMapper;
import kr.dklog.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final StudentMapper studentMapper;

    private final MemberMapper memberMapper;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributesDto attributes = OAuthAttributesDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        StudentDto sessionStudentDto = (StudentDto) httpSession.getAttribute("studentDto");
        if (sessionStudentDto == null) {
            sessionStudentDto = studentMapper.findByGithubUsername(attributes.getGithubUsername())
                    .orElseThrow(() -> new OAuth2AuthenticationException("휴대폰 인증 후에 로그인 서비스를 이용해주세요."));
        }

        sessionStudentDto.setGithubUsername(attributes.getGithubUsername());
        studentMapper.updateStudent(sessionStudentDto);
        MemberDto memberDto = saveOrUpdate(attributes, sessionStudentDto.getStudentId(), sessionStudentDto.getName());
        httpSession.setAttribute("member", new SessionMember(sessionStudentDto, memberDto));
        httpSession.removeAttribute("studentDto");

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(memberDto.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private MemberDto saveOrUpdate(OAuthAttributesDto attributes, Long studentId, String name) {
        Optional<MemberDto> oldMemberDto = memberMapper.findByGithubUsername(attributes.getGithubUsername());

        MemberDto newMemberDto = attributes.toMemberDto(studentId, name);
        if (oldMemberDto.isPresent()) {
            newMemberDto.setMemberId(oldMemberDto.get().getMemberId());
            memberMapper.updateMember(newMemberDto);
        } else {
            memberMapper.save(newMemberDto);
        }

        return memberMapper.findById(newMemberDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
    }
}
