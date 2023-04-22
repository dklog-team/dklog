package kr.dklog.controller;

import kr.dklog.dto.TestDto;
import kr.dklog.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<TestDto> getTestData(@RequestParam Long id) {
        TestDto testDto = testService.get(id);
        return ResponseEntity.ok().body(testDto);
    }

    @GetMapping("/view")
    public String testView(@RequestParam Long id, Model model) {
        TestDto testDto = testService.get(id);
        model.addAttribute(testDto);

        return "view/test";
    }
}
