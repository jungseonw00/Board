package com.study.home.controller;

import com.study.home.dto.User;
import com.study.home.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final UserService userService;

    // 메인 페이지
    @GetMapping("/")
    public String home() {
        log.info("HomePageController home() Start...");
        return "home/home";
    }

    // 로그인
    @PostMapping("/")
    public String login(User params) {
        int result = userService.selectUserId(params);
        // 로그인 정보가 있을경우
        if (result > 0) {
            // userService.selectUser(params);
        // 로그인 정보가 없을경우
        } else {
            return "home/fail";
        }
        return "home/success";
    }
}
