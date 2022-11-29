package com.study.home.controller;

import com.study.home.dto.User;
import com.study.home.model.UserMapper;
import com.study.home.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeApiController {

    private final UserService userService;

    @PostMapping("duplicateId")
    public String duplicateId(@RequestBody String userId) {
        User vo = new User();
        vo.setUserId(userId);
        int result = userService.selectUserId(vo);
        log.info("result = " + result);
        if (result > 0) {
            return "duplicate";
        } else {
            return "ok";
        }
    }
}
