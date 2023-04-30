package kr.dklog.controller;

import kr.dklog.common.session.LoginMember;
import kr.dklog.common.session.SessionMember;
import kr.dklog.dto.common.RequestListDto;
import kr.dklog.dto.response.ResponsePostListDto;
import kr.dklog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model, @LoginMember SessionMember sessionMember, @ModelAttribute RequestListDto requestListDto) {
        if (sessionMember != null) {
            model.addAttribute("member", sessionMember);
        }

        log.info("page: {}", requestListDto.getPage());
        log.info("pageSize: {}", requestListDto.getPageSize());
        ResponsePostListDto responsePostListDto = postService.getList(requestListDto);
        model.addAttribute("responsePostListDto", responsePostListDto);

        return "index";
    }
}
