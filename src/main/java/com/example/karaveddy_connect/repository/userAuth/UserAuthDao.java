package com.example.karaveddy_connect.repository.userAuth;

import com.example.karaveddy_connect.dto.request.UserAuthLoginReq;
import com.example.karaveddy_connect.dto.response.UserAuthLoginResponse;

public interface UserAuthDao {

    public UserAuthLoginResponse getUserAuthByEmail(String email);

}
