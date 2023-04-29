package kr.dklog.common.session;

import kr.dklog.dto.MemberDto;
import kr.dklog.dto.StudentDto;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private Long studentId;

    private Long memberId;

    private String githubUsername;

    private String email;

    private String picture;

    private String username;

    public SessionMember(StudentDto studentDto, MemberDto memberDto) {
        this.studentId = studentDto.getStudentId();
        this.memberId = memberDto.getMemberId();
        this.githubUsername = memberDto.getGithubUsername();
        this.email = memberDto.getEmail();
        this.picture = memberDto.getPicture();
        this.username = studentDto.getSemester() + "기_" + studentDto.getName();
    }
}
