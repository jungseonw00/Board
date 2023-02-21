package com.study.home.controller;

import com.study.home.dto.UserRequestDto;
import com.study.home.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeApiController {

    private final UserService userService;

    @PostMapping("/duplicateId")
    public String duplicateId(@RequestBody UserRequestDto params) {
        log.info("userId = " + params.getUserId());
        int result = userService.selectUserId(params);
        log.info("result = " + result);
        if (result > 0) {
            return "duplicate";
        } else {
            return "ok";
        }
    }

    @PostMapping("/createUser")
    public Long saveUser(UserRequestDto params) {
        userService.saveUser(params);
        return null;
    }
}
