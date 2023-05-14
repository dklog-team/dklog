package kr.dklog.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestDeleteCommentDto {

    private Long postId;

    private Long commentId;

    private Long memberId;
}
