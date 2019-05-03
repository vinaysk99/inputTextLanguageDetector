package com.vk.languageDetector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Getter
@Builder
public class ResultDto implements Serializable {

    private static final long serialVersionUID = 5425911325251191557L;

    @JsonProperty("languagesMatched")
    private List<String> languagesMatched;

    @JsonCreator
    public ResultDto(@JsonProperty("languagesMatched") List<String> languagesMatched) {
        this.languagesMatched = languagesMatched;
    }

    public List<String> getLanguagesMatched() {
        return languagesMatched;
    }
}
