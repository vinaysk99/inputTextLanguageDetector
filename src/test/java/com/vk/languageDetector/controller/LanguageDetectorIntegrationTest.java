package com.vk.languageDetector.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.languageDetector.Application;
import com.vk.languageDetector.dto.Dto;
import com.vk.languageDetector.dto.InputDto;
import com.vk.languageDetector.dto.ResultDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LanguageDetectorIntegrationTest {

    private String endpoint = "/v1/detectLanguageForText";

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected Object postObjectToRESTEndPoint(
            Object data, TypeReference typeReference) {
        Dto<Object> objectDto = null;
        try {
            String response = mockMvc.perform(post(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(data)))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            objectDto = mapper.readValue(response, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectDto.getData();
    }

    @Test
    public void shouldGetEnglishWhenLookedUpForEnglishText() {
        TypeReference typeReference = new TypeReference<Dto<ResultDto>>() {};
        InputDto inputDto = new InputDto("Hello");
        ResultDto resultDto = (ResultDto) postObjectToRESTEndPoint(inputDto, typeReference);
        assertEquals(resultDto.getLanguagesMatched().size(), 1);
        assertEquals(resultDto.getLanguagesMatched().get(0), "ENGLISH");
    }

    @Test
    public void shouldGetFrenchWhenLookedUpForFrenchText() {
        TypeReference typeReference = new TypeReference<Dto<ResultDto>>() {};
        InputDto inputDto = new InputDto("lōwät");
        ResultDto resultDto = (ResultDto) postObjectToRESTEndPoint(inputDto, typeReference);
        assertEquals(resultDto.getLanguagesMatched().size(), 1);
        assertEquals(resultDto.getLanguagesMatched().get(0), "FRENCH");
    }

    @Test
    public void shouldGetNothingWhenLookedUpForInvalidChars() {
        TypeReference typeReference = new TypeReference<Dto<ResultDto>>() {};
        InputDto inputDto = new InputDto("-+===");
        ResultDto resultDto = (ResultDto) postObjectToRESTEndPoint(inputDto, typeReference);
        assertEquals(resultDto.getLanguagesMatched().size(), 0);
    }

    @Test
    public void shouldGetNothingWhenLookedUpForEmptyInput() {
        InputDto inputDto = new InputDto("");
        try {
            mockMvc.perform(post(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(inputDto)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

