package kr.dklog.dto.common;

import kr.dklog.common.type.Role;
import kr.dklog.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributesDto {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String githubUsername;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributesDto(Map<String, Object> attributes, String nameAttributeKey, String name, String githubUsername, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.githubUsername = githubUsername;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributesDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGithub(userNameAttributeName, attributes);
    }

    public static OAuthAttributesDto ofGithub(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributesDto.builder()
                .githubUsername((String) attributes.get("login"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public MemberDto toMemberDto(Long studentId, Integer semester, String name) {
        return MemberDto.builder()
                .githubUsername(githubUsername)
                .username(semester + "기_" + name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .studentId(studentId)
                .build();
    }
}
