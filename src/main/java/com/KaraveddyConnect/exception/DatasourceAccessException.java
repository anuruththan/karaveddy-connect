package com.KaraveddyConnect.exception;

import lombok.Setter;

import java.io.Serial;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:58 PM
 **/
@Setter
public class DatasourceAccessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private String message;

    public DatasourceAccessException() {
    }

    public DatasourceAccessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
