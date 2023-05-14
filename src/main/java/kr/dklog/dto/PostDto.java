package kr.dklog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
