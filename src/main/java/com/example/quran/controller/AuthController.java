package com.example.quran.controller;

import com.example.quran.dto.LoginRequest;
import com.example.quran.dto.SignupRequest;
import com.example.quran.jwt.AuthenticationResponse;
import com.example.quran.jwt.JwtUtils;
import com.example.quran.repository.RoleRepository;
import com.example.quran.repository.UsersRepository;
import com.example.quran.response.MessageResponse;
import com.example.quran.response.UserInfoResponse;
import com.example.quran.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest){
        AuthenticationResponse authenticationResponse = authService.loginUser(loginRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authenticationResponse.getJwt())
                .body(new UserInfoResponse(authenticationResponse.getId(),
                        authenticationResponse.getUsername(),
                        authenticationResponse.getEmail(),
                        authenticationResponse.getRoles(),
                        authenticationResponse.getJwt()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User Registered Successful"));
    }
}
