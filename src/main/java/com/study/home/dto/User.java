package com.study.home.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public User(String userId, String password, String name, String email, String phone, String address, String detailAddress, char deleteAt, LocalDateTime createdDate) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.detailAddress = detailAddress;
        this.deleteAt = deleteAt;
        this.createdDate = createdDate;
    }
}
