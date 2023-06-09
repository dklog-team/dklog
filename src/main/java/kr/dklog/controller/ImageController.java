package kr.dklog.controller;

import kr.dklog.dto.request.RequestUploadImageDto;
import kr.dklog.dto.response.ResponseUploadResultDto;
import kr.dklog.service.FileTransferService;
import kr.dklog.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ImageController {

    @Value("${image.root-url}")
    private String rootUrl;

    @Value("${image.upload-path}")
    private String localPath;

    private final FileUploadService fileUploadService;

    private final FileTransferService fileTransferService;

    @PostMapping("/image/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@ModelAttribute RequestUploadImageDto requestDto){

        ResponseUploadResultDto responseDto = fileUploadService.uploadImage(requestDto);
        fileTransferService.uploadFile(localPath + "/" + responseDto.getStoreName() + responseDto.getFileType(), "/home/cheoljin/Documents/web/dklog-upload-image");

        responseDto.setImageURL(responseDto.getLocation() + "/"+ responseDto.getStoreName() + responseDto.getFileType());

        return ResponseEntity.ok().body(rootUrl + "/" + responseDto.getStoreName() + responseDto.getFileType());
    }
}