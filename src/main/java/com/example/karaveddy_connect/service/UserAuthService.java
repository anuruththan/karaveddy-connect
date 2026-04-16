package com.example.karaveddy_connect.service;

import com.example.karaveddy_connect.dto.request.UserAuthLoginReq;
import com.example.karaveddy_connect.dto.response.GeneralAuthResponse;
import com.example.karaveddy_connect.dto.response.GeneralResponse;

public interface UserAuthService {

    public GeneralAuthResponse getUserAuthByEmail(UserAuthLoginReq userAuthLoginReq);


}
