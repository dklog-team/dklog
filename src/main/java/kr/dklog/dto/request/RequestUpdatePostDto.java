package kr.dklog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RequestUpdatePostDto {

    private Long postId;
    private String title;
    private String contentMd;
    private String contentHtml;
    private String contentText;
    private LocalDateTime modifiedDate;

}
