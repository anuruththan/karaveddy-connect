package com.example.karaveddy_connect.dto.response;

import com.example.karaveddy_connect.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthLoginResponse {
    private String username;
    private String password;
    private Roles role;
}
