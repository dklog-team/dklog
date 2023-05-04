package kr.dklog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateCommentDto {

    private Long commentId;

    private String content;

    private Long postId;

    private Long memberId;

    private LocalDateTime modifiedDate;
}
