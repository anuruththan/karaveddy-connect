package com.example.karaveddy_connect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse {
    private Object data;
    private String msg;
    private int status;
    private boolean res;
}
