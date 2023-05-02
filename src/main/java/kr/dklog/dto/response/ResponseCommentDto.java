package kr.dklog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentDto {
    private Long commendID;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private Long postId;
}
