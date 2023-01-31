package com.study;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarExcelDto {

	private final String company; // 회사
	private final String name; // 차종
	private final int price; // 가격
	private final double rating; // 평점
}
