package com.study.home.model;

import com.study.home.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUserInfo(User user);

    int selectUserId(User user);

    User selectUser(User user);
}
