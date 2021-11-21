package com.example.survey.service.questionnaire;

import com.example.survey.dao.entity.OptionEntity;
import com.example.survey.dao.entity.QuestionEntity;
import com.example.survey.dao.entity.QuestionnaireEntity;
import com.example.survey.dao.repository.QuestionnaireRepository;
import com.example.survey.exception.ResourceNotFoundException;
import com.example.survey.mapper.QuestionnaireMapper;
import com.example.survey.model.enums.QuestionnaireStatus;
import com.example.survey.model.enums.Status;
import com.example.survey.model.general.ApiResponse;
import com.example.survey.model.general.PaginatedResponse;
import com.example.survey.model.questionnaire.QuestionnaireDTO;
import com.example.survey.model.questionnaire.QuestionnaireListItemDTO;
import com.example.survey.model.questionnaire.QuestionnairePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;

    public ApiResponse<?> getPagedQuestionnaires(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<QuestionnaireEntity> pageData = questionnaireRepository.findAllQuestionnaires(pageable);
        return ApiResponse.<PaginatedResponse<List<QuestionnaireListItemDTO>>>builder()
                .data(QuestionnaireMapper.INSTANCE.mapSearchResponse(pageData))
                .buildSuccess();
    }

    public void deleteQuestionnaire(Long id) {
        questionnaireRepository.findByIdAndStatus(id, Status.ACTIVE).ifPresent(questionnaireEntity -> {
            if (questionnaireEntity.getQuestionnaireStatus() == QuestionnaireStatus.SCHEDULED) {
                questionnaireEntity.setStatus(Status.INACTIVE);
                questionnaireRepository.save(questionnaireEntity);
            }
        });
    }

    public ApiResponse<?> getQuestionnaireById(Long id) {
        Optional<QuestionnaireEntity> questionnaireEntity = questionnaireRepository.findByIdAndStatus(id, Status.ACTIVE);
        if (questionnaireEntity.isPresent()) {
           var questionnaireDTO=QuestionnaireMapper.INSTANCE.entityToDTO(questionnaireEntity.get());
            return ApiResponse.<QuestionnaireDTO>builder()
                    .data(questionnaireDTO)
                    .buildSuccess();
        } else {
            throw new ResourceNotFoundException("Resource not found");
        }

    }



    public void saveQuestionnaire(QuestionnairePayload questionnairePayload) {
        QuestionnaireEntity questionnaire = new QuestionnaireEntity();
        questionnaire.setTitle(questionnairePayload.getTitle());
        questionnaire.setDescription(questionnairePayload.getDescription());
        questionnaire.setMandatory(false);
        questionnaire.setStatus(Status.ACTIVE);

        if (questionnairePayload.isNow()) {
            questionnaire.setQuestionnaireStatus(QuestionnaireStatus.FINISHED);
            questionnaire.setScheduledAt(LocalDateTime.now());
        } else {
            if (questionnairePayload.getScheduledAt() == null
                    || questionnairePayload.getScheduledAt().isBefore(LocalDateTime.now()))
                throw new RuntimeException("Invalid date");

            questionnaire.setScheduledAt(questionnairePayload.getScheduledAt());
            questionnaire.setQuestionnaireStatus(QuestionnaireStatus.SCHEDULED);
        }

        questionnairePayload.getQuestions().forEach(question -> {
            QuestionEntity newQuestion = new QuestionEntity();
            newQuestion.setQuestionType(question.getType());
            newQuestion.setOrderId(question.getOrderId());
            newQuestion.setContent(question.getContent());
            newQuestion.setTitle(question.getTitle());
            newQuestion.setQuestionnaireEntity(questionnaire);
            question.getOptions().forEach(option -> {
                OptionEntity newOption = new OptionEntity();
                newOption.setContent(option.getContent());
                newOption.setNextQuestionOrderId(option.getNextQuestionOrderId());
                newOption.setQuestionEntity(newQuestion);
                newQuestion.getOptionEntities().add(newOption);
            });
            questionnaire.getQuestionEntities().add(newQuestion);
        });

        questionnaireRepository.save(questionnaire);
    }

    public void updateQuestionnaire(Long id, QuestionnairePayload questionnairePayload) {
        Optional<QuestionnaireEntity> fromDb = questionnaireRepository.findByIdAndStatus(id, Status.ACTIVE);

        if (fromDb.isPresent() && fromDb.get().getQuestionnaireStatus() == QuestionnaireStatus.SCHEDULED) {
            var questionnaire = fromDb.get();
            questionnaire.setTitle(questionnairePayload.getTitle());
            questionnaire.setDescription(questionnairePayload.getDescription());
            questionnaire.setQuestionnaireStatus(QuestionnaireStatus.SCHEDULED);
            questionnaire.getQuestionEntities().clear();

            if (questionnairePayload.isNow()) {
                questionnaire.setQuestionnaireStatus(QuestionnaireStatus.FINISHED);
                questionnaire.setScheduledAt(LocalDateTime.now());
            } else {
                if (questionnairePayload.getScheduledAt() == null
                        || questionnairePayload.getScheduledAt().isBefore(LocalDateTime.now()))
                    throw new RuntimeException("Invalid date");

                questionnaire.setScheduledAt(questionnairePayload.getScheduledAt());
                questionnaire.setQuestionnaireStatus(QuestionnaireStatus.SCHEDULED);
            }

            questionnairePayload.getQuestions().forEach(question -> {
                QuestionEntity newQuestion = new QuestionEntity();
                newQuestion.setQuestionType(question.getType());
                newQuestion.setOrderId(question.getOrderId());
                newQuestion.setContent(question.getContent());
                newQuestion.setTitle(question.getTitle());
                newQuestion.setQuestionnaireEntity(questionnaire);
                question.getOptions().forEach(option -> {
                    OptionEntity newOption = new OptionEntity();
                    newOption.setContent(option.getContent());
                    newOption.setNextQuestionOrderId(option.getNextQuestionOrderId());
                    newOption.setQuestionEntity(newQuestion);
                    newQuestion.getOptionEntities().add(newOption);
                });
                questionnaire.getQuestionEntities().add(newQuestion);
            });

            questionnaireRepository.save(questionnaire);
        } else {
            throw new ResourceNotFoundException("Resource not found");
        }
    }
}