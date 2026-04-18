package com.example.karaveddy_connect.exception;


import com.example.karaveddy_connect.dto.response.GeneralResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private GeneralResponse generalResponse;

    /**
     * Responds to validation errors with unauthorized status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HttpHeaders headers = new HttpHeaders();
        addClearCookie(headers, "ACCESS_TOKEN");
        addClearCookie(headers, "REFRESH_TOKEN");
        addClearCookie(headers, "USER_NAME");
        addClearCookie(headers, "USER_ROLE");
        generalResponse = new GeneralResponse(null, "Your JWT token has expired. Please log in again.", HttpStatus.UNAUTHORIZED.value(), false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(generalResponse);
    }

    /**
     * Handles illegal argument exceptions; returns bad request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GeneralResponse> handleIllegalArg(IllegalArgumentException ex) {
        generalResponse = new GeneralResponse(null, ex.getMessage(), 401, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generalResponse);
    }


    /**
     * This method is used to clear the cookie
     * */
    private void addClearCookie(HttpHeaders headers, String name) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
