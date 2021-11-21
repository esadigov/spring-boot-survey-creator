package com.example.survey.service.questionnaire;


import com.example.survey.model.general.ApiResponse;
import com.example.survey.model.questionnaire.QuestionnairePayload;

public interface QuestionnaireService {
    void saveQuestionnaire(QuestionnairePayload questionnairePayload);

    ApiResponse<?> getPagedQuestionnaires(Integer pageIndex, Integer pageSize);

    ApiResponse<?> getQuestionnaireById(Long id);

    void deleteQuestionnaire(Long id);

    void updateQuestionnaire(Long id, QuestionnairePayload questionnairePayload);

}

