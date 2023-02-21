package com.study.home;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HomeTests {

    @Autowired
    UserMapper userDao;

    @DisplayName("아이디 중복 검사")
    @Test
    void duplicateUserId() {
        String userId = "seonwoojung";
        int result = userDao.selectCountUser(userId);
        assertThat(result).isEqualTo(1);
        System.out.println("result = " + result);
    }
}
