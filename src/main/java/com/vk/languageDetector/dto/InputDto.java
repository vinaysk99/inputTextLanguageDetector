package com.vk.languageDetector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Getter
@Builder
public class InputDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("text")
    private String text;

    @JsonCreator
    public InputDto(@JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
