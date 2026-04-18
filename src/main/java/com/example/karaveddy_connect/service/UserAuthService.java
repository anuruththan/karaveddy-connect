package com.example.karaveddy_connect.service;

import com.example.karaveddy_connect.dto.request.UserAuthLoginReq;
import com.example.karaveddy_connect.dto.response.GeneralAuthResponse;
import com.example.karaveddy_connect.dto.response.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface UserAuthService {

    GeneralAuthResponse getUserAuthByEmail(UserAuthLoginReq userAuthLoginReq);

    GeneralAuthResponse getRefreshAuth(HttpServletRequest request);


}
