package com.study.home.controller;

import com.study.home.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeApiController {

    private final UserService userService;

    @PostMapping("/duplicateId")
    public String duplicateId() {
        return "ok";
    }
}
