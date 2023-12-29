package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException{

    private HttpStatus status; // 모든 예외를 여기서부터 가져온다.

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status; // 상태 코드
    }

    public HttpStatus getStatus() {
        return status;
    }
}
