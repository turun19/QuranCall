package com.example.quran.dto;


import com.example.quran.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @NotNull(message = "Username kosong")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email(message = "invalid, you must create email")
    @NotNull(message = "Email Kosong")
    private String email;

    @NotBlank
    @Size(min = 8, max = 40, message = "Minimal 8 Karakter!")
    @NotNull
    private String password;
    @NotNull
    private Set<String> role;
}
