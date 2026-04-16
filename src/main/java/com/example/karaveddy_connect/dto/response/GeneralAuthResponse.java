package com.example.karaveddy_connect.dto.response;

import com.example.karaveddy_connect.enums.Roles;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralAuthResponse {
    private Object data;
    private String msg;
    private int status;
    private boolean res;
    private String username;
    private Roles role;
    private String accessToken;
    private String refreshToken;
}
