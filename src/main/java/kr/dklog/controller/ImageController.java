package kr.dklog.controller;

import kr.dklog.dto.request.RequestUploadImageDto;
import kr.dklog.dto.response.ResponseUploadResultDto;
import kr.dklog.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final FileUploadService fileUploadService;

    @PostMapping("/image/upload")
    @ResponseBody
    public ResponseEntity<?> upload(@ModelAttribute RequestUploadImageDto requestDto){

        ResponseUploadResultDto responseDto = fileUploadService.uploadImage(requestDto);

        return ResponseEntity.ok().body(responseDto);

    }
}
