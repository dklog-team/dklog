package kr.dklog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long memberId;
    private Long postId;
}
