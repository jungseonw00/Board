package com.study.home.controller;

import com.study.home.dto.User;
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
    public String login(HttpSession session, Model model, User params) {
        // 로그인 검증
        int result = userService.selectUserId(params);
        if (result > 0) {
            session.setAttribute("user", userService.selectUser(params));
            model.addAttribute("user", session.getAttribute("user"));
            return "home/success";
        } else {
            log.info("로그인 실패");
            return "home/fail";
        }
    }

    // 회원가입
    @GetMapping("/createUser")
    public String createUser() {
        return "home/createForm";
    }

    @PostMapping("/createUser")
    public String createNewUser(Model model, User params) {
        int result = userService.insertUser(params);
        log.info("result ==================> " + result);
        // 가입이 완료 됬을 때
        if (result > 0) {
            return "redirect:/";
            // 가입에 실패했을 때
        } else {
            model.addAttribute("fail", "fail");
            return "";
        }
    }
}
