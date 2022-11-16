package com.study.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 예외 응답을 처리할 Response 클래스
 */
@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
