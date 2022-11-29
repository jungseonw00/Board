package com.study.home.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @Data: @Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
 */
@Data
@Slf4j
public class User {

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
