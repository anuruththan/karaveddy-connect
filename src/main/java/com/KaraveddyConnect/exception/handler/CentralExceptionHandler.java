package com.KaraveddyConnect.exception.handler;

import com.KaraveddyConnect.dto.response.GeneralResponse;
import com.KaraveddyConnect.exception.BadRequestException;
import com.KaraveddyConnect.exception.DatasourceAccessException;
import com.KaraveddyConnect.exception.UnAuthorizedException;
import com.KaraveddyConnect.exception.constant.ExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:49 PM
 **/
@RestControllerAdvice
@Slf4j
public class CentralExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<GeneralResponse> badRequestExceptionHandler(BadRequestException ex, WebRequest webRequest) {
        log.error("badRequestExceptionHandler invoked", ex);
        short statusCode = ex.getStatusCode() > 0
                ? ex.getStatusCode()
                : ExceptionConstant.COMMON_EXCEPTION;

        String message = (ex.getMessage() != null && !ex.getMessage().trim().isEmpty())
                ? ex.getMessage()
                : ExceptionConstant.getDescription(statusCode);

        return ResponseEntity.ok().body(new GeneralResponse(null, message, statusCode, false));
    }

    @ExceptionHandler(DatasourceAccessException.class)
    @ResponseBody
    public ResponseEntity<?> datasourceAccessExceptionHandler(DatasourceAccessException datasourceAccessException, WebRequest webRequest) {
        log.error("datasourceAccessExceptionHandler invoked ", datasourceAccessException);
        GeneralResponse generalAPIResponse = new GeneralResponse();
        generalAPIResponse.setData(null);
        generalAPIResponse.setMsg(ExceptionConstant.getDescription((short) 1006));
        generalAPIResponse.setStatusCode(1036);
        generalAPIResponse.setRes(false);
        return new ResponseEntity<>(generalAPIResponse, HttpStatus.OK);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseBody
    public ResponseEntity<?> unAuthorizedExceptionHandler(UnAuthorizedException forbiddenException, WebRequest webRequest) {
        log.error("unAuthorizedExceptionHandler invoked ", forbiddenException);
        GeneralResponse generalAPIResponse = new GeneralResponse();
        generalAPIResponse.setData(null);
        generalAPIResponse.setMsg(ExceptionConstant.getDescription((short) 401));
        generalAPIResponse.setStatusCode(401);
        generalAPIResponse.setRes(false);
        return new ResponseEntity<>(generalAPIResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    private ResponseEntity<?> messageNotReadableExceptionHandler() {
        log.error("messageNotReadableExceptionHandler invoked ");
        GeneralResponse generalAPIResponse = new GeneralResponse();
        generalAPIResponse.setData(null);
        generalAPIResponse.setMsg(ExceptionConstant.getDescription((short) 1006));
        generalAPIResponse.setStatusCode( (short) 1006);
        generalAPIResponse.setRes(false);
        return new ResponseEntity<>(generalAPIResponse, HttpStatus.BAD_REQUEST);
    }
}