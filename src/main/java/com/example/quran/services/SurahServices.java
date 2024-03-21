package com.example.quran.services;


import com.example.quran.model.Surah;
import com.example.quran.repository.SurahRepository;
import com.example.quran.response.SurahResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurahServices {
    @Autowired
    SurahRepository surahRepository;

    public List<SurahResponse> getSurah(){
        List<SurahResponse> surahResponses = new ArrayList<>();
        List<Surah> surahs = surahRepository.findAllByOrderByNumberAsc();
        for(Surah surahList : surahs){
            SurahResponse surahResponse = new SurahResponse();
            surahResponse.setNumber(surahList.getNumber());
            surahResponse.setSurahName(surahList.getNameId());
            surahResponse.setTranslateId(surahList.getTranslationId());
            surahResponses.add(surahResponse);
        }
        return surahResponses;
    }

    public List<Surah> getSurahLike(String surahName){
        return surahRepository.findByFullTextSearch(surahName);
    }}
