package org.example.qnaproject.user.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsUsernameException extends UserException {

    private static final String MESSAGE = "이미 가입된 닉네임입니다.";

    public AlreadyExistsUsernameException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
