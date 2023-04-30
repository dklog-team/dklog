package kr.dklog.service;


import kr.dklog.dto.ImageDto;
import kr.dklog.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;

    public String insertImage(ImageDto imageDto) {

        MultipartFile image = imageDto.getImage();

        String path = new File("c:/images/").getAbsolutePath();

        String imgExtension = "";
        if (!image.isEmpty()) {
            switch (image.getContentType()) {
                case "image/jpg":
                    imgExtension = ".jpg";
                    break;
                case "image/jpeg":
                    imgExtension = ".jpeg";
                    break;
                case "image/png":
                    imgExtension = ".png";
                    break;
                case "image/gif":
                    imgExtension = ".gif";
                    break;
                default:
                    throw new IllegalArgumentException("허용되지 않은 파일 형식입니다.");
            }
            imageDto.setFileType(imgExtension);
        }

        String newImgName = UUID.randomUUID().toString();

        imageDto.setUploadName(image.getOriginalFilename());
        imageDto.setStoreName(newImgName);
        imageDto.setLocation(path);

        imageMapper.insertImage(imageDto);

        File file = new File(imageDto.getLocation() + "/" + imageDto.getStoreName() + imageDto.getFileType());

        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageDto.getLocation() + "/" + imageDto.getStoreName() + imageDto.getFileType();
    }

}
