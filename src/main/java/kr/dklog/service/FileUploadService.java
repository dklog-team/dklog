package kr.dklog.service;

import kr.dklog.dto.request.RequestUploadImageDto;
import kr.dklog.dto.response.ResponseUploadResultDto;
import kr.dklog.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${image.upload-path}")
    private String imageUploadPath;

    private final ImageMapper imageMapper;

    public ResponseUploadResultDto uploadImage(RequestUploadImageDto requestDto) {

        System.out.println("imageUploadPath: " + imageUploadPath);

        MultipartFile image = requestDto.getImage();


        String path = new File(imageUploadPath).getAbsolutePath();

        ResponseUploadResultDto responseDto = new ResponseUploadResultDto();

        String imgExtension = null;

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
            System.out.println("imageUploadPath: " + imageUploadPath);
            responseDto.setFileType(imgExtension);
            System.out.println("imageUploadPath: " + imageUploadPath);
        }

        String newImgName = UUID.randomUUID().toString();

        responseDto.setUploadName(image.getOriginalFilename());
        responseDto.setStoreName(newImgName);
        responseDto.setLocation(path);

        imageMapper.save(responseDto);

        File file = new File(responseDto.getLocation()+"/"  + responseDto.getStoreName() + responseDto.getFileType());

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

        return responseDto;
    }

}
