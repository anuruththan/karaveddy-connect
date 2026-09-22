package com.KaraveddyConnect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:59 PM
 **/
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UnAuthorizedException(String message) {
        super(message);
    }
}
