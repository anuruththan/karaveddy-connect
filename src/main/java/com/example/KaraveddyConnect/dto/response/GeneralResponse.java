package com.example.KaraveddyConnect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:00 PM
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {
    Object data;
    String msg;
    int statusCode;
    boolean res;
}
