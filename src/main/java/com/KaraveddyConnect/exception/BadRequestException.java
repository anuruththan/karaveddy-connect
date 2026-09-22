package com.KaraveddyConnect.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:50 PM
 **/
@Getter
@Setter
public class BadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private short statusCode;
    private String metaData;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(short statusCode) {
        this.statusCode = statusCode;
    }

    public BadRequestException(String message, short statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public BadRequestException(String message, short statusCode, String metaData) {
        super(message);
        this.statusCode = statusCode;
        this.metaData = metaData;
    }
}
