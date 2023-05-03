package kr.dklog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentDto {
    private Long commendID;
    private String content;
    private String writeDate;
    private Long postId;
    private Long memberId;
    private String username;
}
