package com.example.survey.model.questionnaire;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class OptionPayload {
    private long optionId;

    @NotNull
    private String content;
    private int nextQuestionOrderId;
}
