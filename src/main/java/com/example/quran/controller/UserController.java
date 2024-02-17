package com.example.quran.controller;

import com.example.quran.model.Users;
import com.example.quran.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public Optional<Users> getUserById(Long id){
        return userService.getUserById(id);
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
}
