package com.example.quran.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class AuthenticationResponse {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<String> roles;
    private String jwt;

    public AuthenticationResponse(Long id, String username, String email, String password, List<String> roles, String jwt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.jwt = jwt;
    }
}
