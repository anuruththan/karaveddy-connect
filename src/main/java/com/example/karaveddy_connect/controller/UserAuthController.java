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

    @Value("${REFRESH_TOKEN_EXPIRATION_Time}")
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

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody UserAuthLoginReq userAuthLoginReq) {
        GeneralAuthResponse generalAuthResponse = userAuthService.getUserAuthByEmail(userAuthLoginReq);
        generalResponse = new GeneralResponse(generalAuthResponse.getData(), generalAuthResponse.getMsg(), generalAuthResponse.getStatus(), generalAuthResponse.isRes());
        List<String> cookies= generateCookie(generalAuthResponse.getAccessToken(), generalAuthResponse.getRefreshToken(), generalAuthResponse.getUsername(), generalAuthResponse.getRole());
        String accessCookie = cookies.get(0);
        String refreshCookie = cookies.get(1);
        String userEmailCookie = cookies.get(2);
        String userRoleCookie = cookies.get(3);

        return ResponseEntity.status(generalAuthResponse.getStatus())
                .header(HttpHeaders.SET_COOKIE, accessCookie)
                .header(HttpHeaders.SET_COOKIE, refreshCookie)
                .header(HttpHeaders.SET_COOKIE, userEmailCookie)
                .header(HttpHeaders.SET_COOKIE, userRoleCookie)
                .body(generalResponse);

    }

    @GetMapping("/logout")
    public ResponseEntity<GeneralResponse> logout(){
        List<String> cookies= generateCookie(null, null, null, null);
        String accessCookie = cookies.get(0);
        String refreshCookie = cookies.get(1);
        String userEmailCookie = cookies.get(2);
        String userRoleCookie = cookies.get(3);

        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE, accessCookie)
                .header(HttpHeaders.SET_COOKIE, refreshCookie)
                .header(HttpHeaders.SET_COOKIE, userEmailCookie)
                .header(HttpHeaders.SET_COOKIE, userRoleCookie)
                .body(generalResponse);
    }

}
