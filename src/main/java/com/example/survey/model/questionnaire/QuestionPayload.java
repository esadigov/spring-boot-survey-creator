package com.example.survey.model.questionnaire;

import com.example.survey.model.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class QuestionPayload {

    private long questionId;

    @NotBlank
    private QuestionType type;
    @NotBlank
    private int orderId;
    @NotBlank
    private String content;
    @NotBlank
    private String title;
    private List<OptionPayload> options = new ArrayList<>();
}

