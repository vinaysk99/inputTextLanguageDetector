package com.vk.languageDetector.service;

import com.vk.languageDetector.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class LanguageDetectionService {

    private static final Logger log = LoggerFactory.getLogger(LanguageDetectionService.class);

    /**
     * Loads all the characters for each language into a map
     * @return
     */
    private Map<String, String> loadAllWordsFromLanguageFiles() {
        log.info("Reading all the input language files");
        Map<String, String> languageToCharsMap = new HashMap();
        File dir = new File("src/main/resources/languageChars");
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                try {
                    Scanner scanner = new Scanner(file);
                    StringBuilder allWords = new StringBuilder();
                    String language = file.getName().substring(0, file.getName().length() - 2);
                    while (scanner.hasNextLine()) {
                        allWords.append(scanner.nextLine());
                    }
                    String prevChars = "";
                    if (languageToCharsMap.containsKey(language)) {
                        prevChars = languageToCharsMap.get(language);
                    }
                    allWords.append(prevChars);
                    languageToCharsMap.put(language, allWords.toString());
                } catch (IOException e) {
                    log.error("IOException trying to parse text files");
                }
            }
        }
        return languageToCharsMap;
    }

    /**
     * Returns language if found for the input text characters
     *
     * @param text
     * @return
     */
    public List<String> getLanguageForInputText(String text) {
        if (text == null || "".equals(text)) {
            log.error("Invalid input text");
            throw new InvalidDataException("Invalid input text");
        }
        String punctuationChars = ".,;: ";
        Map<String, String> languageToCharsMap = loadAllWordsFromLanguageFiles();
        List<String> results = null;
        if (languageToCharsMap != null && !languageToCharsMap.isEmpty()) {
            results = new ArrayList();
            for (Map.Entry<String, String> entry : languageToCharsMap.entrySet()) {
                String charsInLanguage = entry.getValue();
                int i = 0;
                text = text.toLowerCase();
                for (; i < text.length(); i++) {
                    char ch = text.charAt(i);
                    if (charsInLanguage.indexOf(ch) < 0 && punctuationChars.indexOf(ch) < 0) {
                        break;
                    }
                }
                if (i == text.length()) {
                    log.info("Found language : " + entry.getKey() + " for text : " + entry.getValue());
                    results.add(entry.getKey());
                }
            }
        }
        return results;
    }
}
