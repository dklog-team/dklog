package kr.dklog.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestAddCommentDto {

    private String content;

    private Long postId;

    private Long memberId;
}
