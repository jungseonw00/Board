package com.study.home.controller;

import com.study.home.dto.User;
import com.study.home.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

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
    public String login(HttpSession session, Model model, User params) {
        log.info("login method() start...");
        log.info("params first = " + params);
        // 로그인 검증
        int result = userService.selectUserId(params);
        if (result > 0) {
            log.info("로그인 성공");
            log.info("user = " + userService.selectUser(params));
            session.setAttribute("user", userService.selectUser(params));
            model.addAttribute("user", session.getAttribute("user"));
            return "home/success";
        } else {
            log.info("로그인 실패");
            return "home/fail";
        }


    }
}
