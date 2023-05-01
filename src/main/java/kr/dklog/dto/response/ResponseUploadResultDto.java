package kr.dklog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUploadResultDto {

    private long imageId;

    private String uploadName;

    private String location;

    private String storeName;

    private String fileType;

    private long postId;

}
