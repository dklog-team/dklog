package kr.dklog.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseCommentDto {
    private Long commentId;
    private String content;
    private String writeDate;
    private Long postId;
    private Long memberId;
    private String username;
    private String picture;
}
