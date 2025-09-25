package com.fit.ws.auth.dto;

import lombok.Data;

@Data
public class LoginByGoogle {
    private String name;
    private String email;
    private String picture;
    private String providerId;
    private String username;
    private String password;
}
