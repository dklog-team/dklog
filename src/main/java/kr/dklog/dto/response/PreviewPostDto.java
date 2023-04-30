package kr.dklog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreviewPostDto {

    private Long postId;

    private String title;

    private String previewContent;

    private String previewImage;

    private String createdDate;

    private String modifiedDate;

    private Long memberId;

    private String username;

    private String picture;
}
