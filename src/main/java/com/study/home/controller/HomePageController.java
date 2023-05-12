package com.study.home.controller;

import com.study.home.dto.UserRequestDto;
import com.study.home.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

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
    public String login(HttpSession session, Model model, UserRequestDto params) {
        // 로그인 검증
        return "ok";
    }

    // 회원가입
    @GetMapping("/createUser")
    public String createUser() {
        return "home/createForm";
    }

}
