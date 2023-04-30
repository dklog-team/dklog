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
public class PostDto {

    private Long postId;
    private String title;
    private String contentMd;
    private String contentHtml;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long memberId;
    private String username;
    private String picture;
}
