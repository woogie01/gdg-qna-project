package org.example.qnaproject.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    // 가장 단순한 형태의 생성자로 ErrorCode만 받아서 초기화
    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    // 원인을 포함하는 생성자로 부모 클래스 생성자에 메시지와 원인 전달
    public BusinessException(Throwable cause, ErrorCode errorCode) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
