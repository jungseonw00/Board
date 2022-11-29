package com.study.home.model;

import com.study.home.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public int selectUserId(User params) {
        return userMapper.selectUserId(params);
    }

    @Transactional
    public User selectUser(User params) {
        return userMapper.selectUser(params);
    }

    @Transactional
    public int insertUser(User params) {
        return userMapper.insertUser(params);
    }
}
