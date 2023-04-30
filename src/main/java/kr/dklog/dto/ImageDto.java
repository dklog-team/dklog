package kr.dklog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ImageDto {
    private MultipartFile image;
    private long imageId;
    private String uploadName;
    private String location;
    private String storeName;
    private String fileType;
    private long postId;
}
