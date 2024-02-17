package com.example.quran.repository;

import com.example.quran.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
