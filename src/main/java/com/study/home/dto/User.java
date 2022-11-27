package com.study.home.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
public class User {

    private String userId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String detailAddress;
    private char deleteAt;
    private LocalDateTime createdDate = LocalDateTime.now();

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", deleteAt=" + deleteAt +
                ", createdDate=" + createdDate +
                '}';
    }
}
