package com.example.survey.model.questionnaire;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class QuestionnairePayload {
    @NotBlank
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledAt;

    @NotBlank
    private String description;

    private boolean now;

    @NotEmpty
    private List<QuestionPayload> questions = new ArrayList<>();
}
