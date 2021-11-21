package com.example.survey.mapper;

import com.example.survey.dao.entity.QuestionnaireEntity;
import com.example.survey.model.general.PaginatedResponse;
import com.example.survey.model.questionnaire.OptionPayload;
import com.example.survey.model.questionnaire.QuestionPayload;
import com.example.survey.model.questionnaire.QuestionnaireDTO;
import com.example.survey.model.questionnaire.QuestionnaireListItemDTO;
import com.example.survey.util.DefaultMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class QuestionnaireMapper {
    public static final QuestionnaireMapper INSTANCE = Mappers.getMapper(QuestionnaireMapper.class);
    public abstract QuestionnaireListItemDTO toDTO(QuestionnaireEntity entity);

    public PaginatedResponse<List<QuestionnaireListItemDTO>> mapSearchResponse(Page<QuestionnaireEntity> page){
        if(page==null||page.getContent().isEmpty()){
            return null;
        }
        PaginatedResponse<List<QuestionnaireListItemDTO>> response=new PaginatedResponse<>();
        response.setData(page.getContent().stream().map(this::toDTO).collect(Collectors.toList()));
        return DefaultMapping.mapSearchResponse(response, page);
    }

    public QuestionnaireDTO entityToDTO(QuestionnaireEntity questionnaire){
        QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO();
        questionnaireDTO.setQuestionnaireStatus(questionnaire.getQuestionnaireStatus());
        questionnaire.getQuestionEntities().forEach(questionEntity -> {
            QuestionPayload questionPayload = new QuestionPayload();
            questionPayload.setQuestionId(questionEntity.getId());
            questionPayload.setContent(questionEntity.getContent());
            questionPayload.setTitle(questionEntity.getTitle());
            questionPayload.setOrderId(questionEntity.getOrderId());
            questionPayload.setType(questionEntity.getQuestionType());

            var optionEntities=questionEntity.getOptionEntities();
            var options=new ArrayList<OptionPayload>();
            if(optionEntities!=null){
                optionEntities.forEach(optionEntity -> {
                    var optionPayload = new OptionPayload();
                    optionPayload.setOptionId(optionEntity.getId());
                    optionPayload.setContent(optionEntity.getContent());
                    optionPayload.setNextQuestionOrderId(optionEntity.getNextQuestionOrderId());
                    options.add(optionPayload);
                });
            }

            questionPayload.setOptions(options);
            questionnaireDTO.getQuestions().add(questionPayload);

        });


        questionnaireDTO.setScheduledAt(questionnaire.getScheduledAt());
        questionnaireDTO.setTitle(questionnaire.getTitle());
        questionnaireDTO.setId(questionnaire.getId());
        questionnaireDTO.setDescription(questionnaire.getDescription());
        questionnaireDTO.setQuestionnaireId(questionnaire.getId());

        return questionnaireDTO;
    }
}
