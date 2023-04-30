package kr.dklog.controller;

import kr.dklog.dto.ImageDto;
import kr.dklog.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {



    @GetMapping("/uploadView")
    public String UploadView(){
        return "uploadView";
    }
}

