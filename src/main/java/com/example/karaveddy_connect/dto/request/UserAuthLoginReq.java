package com.example.karaveddy_connect.dto.request;

import lombok.Data;

@Data
public class UserAuthLoginReq {
    private String email;
    private String password;
}
