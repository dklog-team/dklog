package kr.dklog.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDto {

    private Long studentId;

    private String name;

    private String phoneNumber;

    private Integer semester;

    private String authCode;

    private String githubUsername;

    private String authStatus;
}
