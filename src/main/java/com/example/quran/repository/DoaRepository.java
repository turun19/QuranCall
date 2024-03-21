package com.example.quran.repository;

import com.example.quran.model.Doa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoaRepository extends JpaRepository<Doa, Long> {

}
