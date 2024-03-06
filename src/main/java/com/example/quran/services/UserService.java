package com.example.quran.services;

import com.example.quran.dto.ChangePasswordRequest;
import com.example.quran.dto.UserDTO;
import com.example.quran.model.Users;
import com.example.quran.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public Optional<Users> getUserById(Long id){
        log.info("Get Data User By Id Succses!");
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public void uploadPhoto(Long id, MultipartFile file) throws Exception {
        Users users = usersRepository.findById(id)
                .orElseThrow();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            String uploadDir = "user-photos/" + id;
            String filePath = uploadDir + "/" + fileName;
            Path storagePath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(storagePath);
            Files.copy(file.getInputStream(), storagePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            users.setPhotoPath(filePath);
            usersRepository.save(users);
        }catch (IOException e){
            throw new Exception("Could not store file " + fileName + ". Please try again!", e);
        }


    }
    public void changePhoto(Long id, MultipartFile file) throws Exception{
        uploadPhoto(id, file);
    }



//    public void changeUserPassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) throws Exception {
//        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//
//        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), userDetails.getPassword())) {
//            throw new IllegalStateException("Wrong Password");
//        }
//        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())) {
//            throw new IllegalStateException("Password are not the same");
//        }
//
//        // Load the user entity from the repository
//        Optional<Users> userOptional = usersRepository.findByEmail(userDetails.getEmail());
//        if (userOptional.isEmpty()) {
//            throw new IllegalStateException("User not found");
//        }
//        Users user = userOptional.get();
//
//        // Update the password
//        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
//
//        // Save the updated user entity to the repository
//        usersRepository.save(user);
//    }

    public boolean oldPasswordIsValid(Users users, String oldPassword){
        return passwordEncoder.matches(oldPassword, users.getPassword());
    }

    public void changePassword(Users users, String newPassword){
        users.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(users);
    }

}
