package com.example.karaveddy_connect.controller;

import com.example.karaveddy_connect.dto.request.UserAuthLoginReq;
import com.example.karaveddy_connect.dto.response.GeneralAuthResponse;
import com.example.karaveddy_connect.dto.response.GeneralResponse;
import com.example.karaveddy_connect.enums.Roles;
import com.example.karaveddy_connect.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @Value("${ACCESS_TOKEN_EXPIRATION_Time}")
    private int accessTokenExpiryTime;

    @Value("${REFRESH_TOKEN_EXPIATION_TIME}")
    private int cookieExpiryTime;

    GeneralResponse generalResponse;


    private List<String> generateCookie(String accessToken, String refreshToken, String userName, Roles role){
        ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(accessTokenExpiryTime)
                .sameSite("Lax") // or "Strict"
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(cookieExpiryTime)
                .sameSite("Lax") // or "Strict"
                .build();

        ResponseCookie userEmailCookie = ResponseCookie.from("USER_NAME", userName)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(cookieExpiryTime)
                .sameSite("Lax") // or "Strict"
                .build();

        ResponseCookie userRoleCookie = null;

        if(role!=null) {
            userRoleCookie = ResponseCookie.from("USER_ROLE", role.toString())
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(cookieExpiryTime)
                    .sameSite("Lax") // or "Strict"
                    .build();
        }
        else{
            userRoleCookie = ResponseCookie.from("USER_ROLE", null)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(cookieExpiryTime)
                    .sameSite("Lax") // or "Strict"
                    .build();
        }

        return List.of(accessCookie.toString(), refreshCookie.toString(), userEmailCookie.toString(), userRoleCookie.toString());
    }

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

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody UserAuthLoginReq userAuthLoginReq) {
        GeneralAuthResponse generalAuthResponse = userAuthService.getUserAuthByEmail(userAuthLoginReq);
        generalResponse = new GeneralResponse(generalAuthResponse.getData(), generalAuthResponse.getMsg(), generalAuthResponse.getStatus(), generalAuthResponse.isRes());
        List<String> cookies= generateCookie(generalAuthResponse.getAccessToken(), generalAuthResponse.getRefreshToken(), generalAuthResponse.getUsername(), generalAuthResponse.getRole());

        return ResponseEntity.status(generalAuthResponse.getStatus())
                .header(HttpHeaders.SET_COOKIE, cookies.get(0))
                .header(HttpHeaders.SET_COOKIE, cookies.get(1))
                .header(HttpHeaders.SET_COOKIE, cookies.get(2))
                .header(HttpHeaders.SET_COOKIE, cookies.get(3))
                .body(generalResponse);

    }

    @GetMapping("/logout")
    public ResponseEntity<GeneralResponse> logout(){
        HttpHeaders headers = new HttpHeaders();
        addClearCookie(headers, "ACCESS_TOKEN");
        addClearCookie(headers, "REFRESH_TOKEN");
        addClearCookie(headers, "USER_NAME");
        addClearCookie(headers, "USER_ROLE");
        generalResponse = new GeneralResponse(null, "Logout success", 200, true);
        return ResponseEntity.status(200)
                .headers(headers)
                .body(generalResponse);
    }

}
