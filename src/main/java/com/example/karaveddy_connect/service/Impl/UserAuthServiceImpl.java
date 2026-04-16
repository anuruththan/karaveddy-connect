package com.example.karaveddy_connect.service.Impl;

import com.example.karaveddy_connect.dto.request.UserAuthLoginReq;
import com.example.karaveddy_connect.dto.response.GeneralAuthResponse;
import com.example.karaveddy_connect.dto.response.UserAuthLoginResponse;
import com.example.karaveddy_connect.repository.userAuth.UserAuthDao;
import com.example.karaveddy_connect.service.UserAuthService;
import com.example.karaveddy_connect.util.AccessJwtUtil;
import com.example.karaveddy_connect.util.RefreshJwtUtil;
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
        }

        else {
            generalAuthResponse = new GeneralAuthResponse(null,"Invalid email or password", 401, false,null, null, null, null);
        }
        return generalAuthResponse;
    }
}
