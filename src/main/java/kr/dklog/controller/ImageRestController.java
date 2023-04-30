package kr.dklog.controller;

import kr.dklog.dto.ImageDto;
import kr.dklog.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ImageRestController {

    @Autowired
    private final ImageService imageService;

    @PostMapping("/imgUpload")
    public ResponseEntity<Map> uploadImg(ImageDto imageDto){

        String imagePath = imageService.insertImage(imageDto);
        Map response = new HashMap();
        response.put("imagePath", imagePath);

        return ResponseEntity.ok().body(response);
    }
}
