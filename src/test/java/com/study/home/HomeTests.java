package com.study.home;

import com.study.home.dto.User;
import com.study.home.model.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HomeTests {

    @Autowired
    UserMapper userDao;

    @DisplayName("회원가입")
    @Test
    void createUser() {
        // 1.VO에 값을 담는다.
        User params = User.builder()
                .userId("seonwoojung")
                .password("1234")
                .name("정선우")
                .email("laborlawseon@gamil.com")
                .phone("010-4000-1278")
                .address("경기도 고양시 덕양구 원흥 1로 35")
                .detailAddress("1305 - 103")
                .build();

        // 2. DB에 VO값을 insert를 한다.
        userDao.insertUserInfo(params);

        // 3. 값이 정확하게 들어갔는지 확인한다.
        assertThat(userDao.selectUserId(params)).isEqualTo(1);
    }
}
