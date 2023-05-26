package kr.dklog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RequestPostDto {

    private Long postId;
    private String title;
    private String contentMd;
    private String contentHtml;
    private String contentText;
    private LocalDateTime createdDate;
    private Long memberId;

}
