package com.example.quran.controller;

import com.example.quran.dto.ChangePasswordRequest;
import com.example.quran.dto.UserDTO;
import com.example.quran.model.Users;
import com.example.quran.repository.UsersRepository;
import com.example.quran.services.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/user")
    public Optional<Users> getUserById(Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/getEmailUser")
    public Optional<Users> getUserEmail(String email){
        return userService.getUserByEmail(email);
    }

    @PostMapping("/{id}/uploadPhoto")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam MultipartFile file) throws Exception{
        userService.uploadPhoto(id, file);
        return ResponseEntity.ok("Photo Upload Succesfully");
    }

    @PutMapping("/{id}/changePhoto")
    public ResponseEntity<?> changePhoto(@PathVariable Long id, @RequestParam MultipartFile file) throws Exception{
        userService.changePhoto(id, file);
        return ResponseEntity.ok("Photo Changed Successfully");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws Exception{
        Users users = usersRepository.findByEmail(changePasswordRequest.getEmail()).get();
        if(!userService.oldPasswordIsValid(users, changePasswordRequest.getOldPassword())){
            return ResponseEntity.ok("Incorrect old password");
        }
        userService.changePassword(users, changePasswordRequest.getNewPassword());
        return ResponseEntity.ok("Berhasil berubah password");
    }
}
