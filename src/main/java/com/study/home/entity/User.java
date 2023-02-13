package com.study.home.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private Long userSeq;
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
	public User(Long userSeq, String userId, String password, String name, String email, String phone, String phone2, String phone3, String address, String detailAddress, char deleteAt, LocalDateTime createdDate) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.address = address;
		this.detailAddress = detailAddress;
	}
}
