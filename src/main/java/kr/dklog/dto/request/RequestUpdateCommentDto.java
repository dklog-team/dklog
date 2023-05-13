package kr.dklog.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestUpdateCommentDto {

    private Long commentId;

    private String content;

    private Long postId;

    private Long memberId;

    private LocalDateTime modifiedDate;
}
