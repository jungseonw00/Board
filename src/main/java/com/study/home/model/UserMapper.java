package com.study.home.model;

import com.study.home.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {

    void insertUserInfo(User user);

    int selectUserId(User params);

    User selectUser(User user);
}
