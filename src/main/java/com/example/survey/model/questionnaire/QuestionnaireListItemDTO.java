package com.example.survey.model.questionnaire;

import com.example.survey.model.enums.QuestionnaireStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class QuestionnaireListItemDTO {
    private String title;
    private String description;
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd.MM.yyyy HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime scheduledAt;
    private QuestionnaireStatus questionnaireStatus;
}
