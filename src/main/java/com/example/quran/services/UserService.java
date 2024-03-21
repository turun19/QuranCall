package com.example.quran.services;

import com.example.quran.dto.ChangePasswordRequest;
import com.example.quran.dto.UserDTO;
import com.example.quran.model.Users;
import com.example.quran.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public Optional<Users> getUserById(Long id){
        log.info("Get Data User By Id Succses!");
        return usersRepository.findById(id);
    }

//    public Users getUserByToken(String token){
//        return usersRepository.findByToken(token);
//    }

    public Users getUserByEmail(String email){
        return usersRepository.findByEmail1(email);
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


    public boolean checkIfValidOldPassword(Users user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Transactional
    public void changeUserPasswordByEmail(ChangePasswordRequest changePasswordRequest) throws Exception {
        Optional<Users> optionalUser = usersRepository.findByEmail(changePasswordRequest.getEmail());
        log.info(optionalUser + "hai");

        if (optionalUser.isEmpty()) {
            throw new Exception("User with email " + changePasswordRequest.getEmail() + " not found");
        }

        Users user = optionalUser.get();

        if (!checkIfValidOldPassword(user, changePasswordRequest.getOldPassword())) {
            throw new Exception("Old password is incorrect");
        }

        String encodedPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);

        usersRepository.save(user);

    }

}
