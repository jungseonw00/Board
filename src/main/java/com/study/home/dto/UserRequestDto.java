package com.study.home.dto;

import com.study.home.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {
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

	@Builder
	public UserRequestDto(String userId) {
		this.userId = userId;
	}

	@Builder
	public User toEntity() {
		return User.builder()
				.userId(userId)
				.password(password)
				.name(name)
				.email(email)
				.phone(phone)
				.phone2(phone2)
				.phone3(phone3)
				.address(address)
				.detailAddress(detailAddress)
				.build();
	}
}
