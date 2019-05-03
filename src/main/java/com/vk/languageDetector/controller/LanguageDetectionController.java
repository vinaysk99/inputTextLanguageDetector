package com.vk.languageDetector.controller;

import com.vk.languageDetector.dto.Dto;
import com.vk.languageDetector.dto.InputDto;
import com.vk.languageDetector.dto.ResultDto;
import com.vk.languageDetector.service.LanguageDetectionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
public class LanguageDetectionController {

    private static final Logger log = LoggerFactory.getLogger(LanguageDetectionController.class);

    @Autowired
    private LanguageDetectionService languageDetectionService;

    /**
     * Detect language for input text.
     *
     * @param inputDto
     * @param request   Servlet request.
     * @return results matched
     */
    @PostMapping(value = "/v1/detectLanguageForText",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "A short optional description for the GET Endpoint",
            notes = "Implementation Notes on the GET Endpoint")
    public Dto<ResultDto> detectLanguageForText(@RequestBody InputDto inputDto, HttpServletRequest request) {
        log.info("Request to detect language for text : {}", inputDto.getText());
        List<String> languageForInputText = languageDetectionService.getLanguageForInputText(inputDto.getText());
        ResultDto resultDto = new ResultDto(languageForInputText);
        return new Dto(resultDto);
    }
}
