package com.example.karaveddy_connect.service.Impl;

import com.example.karaveddy_connect.dto.request.UserAuthLoginReq;
import com.example.karaveddy_connect.dto.response.GeneralAuthResponse;
import com.example.karaveddy_connect.dto.response.UserAuthLoginResponse;
import com.example.karaveddy_connect.enums.Roles;
import com.example.karaveddy_connect.repository.userAuth.UserAuthDao;
import com.example.karaveddy_connect.service.UserAuthService;
import com.example.karaveddy_connect.util.AccessJwtUtil;
import com.example.karaveddy_connect.util.RefreshJwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.karaveddy_connect.util.HashUtil.hashPwd;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthDao userAuthDao;

    private final AccessJwtUtil accessJwtUtil;

    private final RefreshJwtUtil refreshJwtUtil;

    public GeneralAuthResponse generalAuthResponse;

    @Override
    public GeneralAuthResponse getUserAuthByEmail(UserAuthLoginReq userAuthLoginReq){

        UserAuthLoginResponse userAuthLoginResponse = userAuthDao.getUserAuthByEmail(userAuthLoginReq.getEmail());
        if(userAuthLoginResponse.getPassword().equals(hashPwd(userAuthLoginReq.getPassword()))){
            String accessToken = accessJwtUtil.generateToken(userAuthLoginResponse.getUsername(), userAuthLoginResponse.getRole());
            String refreshToken = refreshJwtUtil.generateToken(userAuthLoginResponse.getUsername(), userAuthLoginResponse.getRole());
            generalAuthResponse = new GeneralAuthResponse(null,"Login success", 200, true,userAuthLoginResponse.getUsername(), userAuthLoginResponse.getRole(), accessToken, refreshToken);
            log.info("User Authenticated successfully");
        }

        else {
            generalAuthResponse = new GeneralAuthResponse(null,"Invalid email or password", 401, false,null, null, null, null);
            log.error("User Authentication failed");
        }
        return generalAuthResponse;
    }


    @Override
    public GeneralAuthResponse getRefreshAuth(HttpServletRequest request){

        try {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) throw new IllegalArgumentException("Missing refresh token.");

            String refreshToken = null;

            for (Cookie cookie : cookies) {
                if ("REFRESH_TOKEN".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }

            if (refreshToken == null) throw new IllegalArgumentException("Missing refresh token.");

            if (!refreshJwtUtil.validateToken(refreshToken))
                throw new IllegalArgumentException("Invalid refresh token.");


            // Validate Refresh Token
            String userName = refreshJwtUtil.extractUserName(refreshToken);
            Roles role = Roles.valueOf(refreshJwtUtil.extractRole(refreshToken));

            if (userName != null) {
                // Generate New Tokens
                String newAccessToken = accessJwtUtil.generateToken(userName, role);
                String newRefreshToken = refreshJwtUtil.generateToken(userName, role);
                log.info("New Access Token Generated");

                return new GeneralAuthResponse(null, "Token Refreshed", 200, true, userName, role, newAccessToken, newRefreshToken);
            }

            else  throw new IllegalArgumentException("Token expired.");
        } catch (Exception e) {
            throw new IllegalArgumentException("No refresh token found.");
        }
    }

}
