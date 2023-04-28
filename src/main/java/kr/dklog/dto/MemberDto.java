package kr.dklog.dto;

import kr.dklog.common.type.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private Long memberId;

    private String githubUsername;

    private String email;

    private String picture;

    private Role role;

    private Long studentId;

    @Builder
    public MemberDto(Long memberId, String githubUsername, String email, String picture, Role role, Long studentId) {
        this.memberId = memberId;
        this.githubUsername = githubUsername;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.studentId = studentId;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
