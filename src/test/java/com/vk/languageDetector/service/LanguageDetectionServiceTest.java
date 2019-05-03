package com.vk.languageDetector.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class LanguageDetectionServiceTest {

    @Autowired
    private LanguageDetectionService languageDetectionService;

    @Test
    public void shouldReturnEnglishLanguageForEnglishCharacters() {
        List<String> languageForInputText = languageDetectionService.getLanguageForInputText("hello, how are you");
        assertEquals(languageForInputText.size(), 1);
        assertEquals(languageForInputText.get(0), "ENGLISH");
    }

    @Test
    public void shouldReturnFrenchLanguageForFrenchCharacters() {
        List<String> languageForInputText = languageDetectionService.getLanguageForInputText("lōwät");
        assertEquals(languageForInputText.size(), 1);
        assertEquals(languageForInputText.get(0), "FRENCH");
    }

    @Test
    public void shouldReturnEmptyForInvalidCharacters() {
        List<String> languageForInputText = languageDetectionService.getLanguageForInputText("محالكرحبا");
        assertEquals(languageForInputText.size(), 0);
    }

    @TestConfiguration
    static class LanguageDetectionServiceTestContextConfiguration {

        @Bean
        public LanguageDetectionService languageDetectionService() {
            return new LanguageDetectionService();
        }
    }
}
