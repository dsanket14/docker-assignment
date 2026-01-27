package com.nagarro.nagp.exception;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    private String code;
    private String message;

    public GenericException() {
    }

    public GenericException(String code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }
}
