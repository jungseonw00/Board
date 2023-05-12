package com.study.home.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
	private String userId;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String phone2;
	private String phone3;
	private String address;
	private String detailAddress;
	private char deleteAt;
	private LocalDateTime createdDate = LocalDateTime.now();
}
