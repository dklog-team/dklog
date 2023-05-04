package kr.dklog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeleteCommentDto {

    private Long postId;

    private Long commentId;

    private Long memberId;
}
