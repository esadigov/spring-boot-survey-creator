package com.example.survey.controller;


import com.example.survey.model.questionnaire.QuestionnairePayload;
import com.example.survey.service.questionnaire.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    @PostMapping
    public ResponseEntity<?> saveQuestionnaire(@Valid @RequestBody QuestionnairePayload questionnairePayload) {
        questionnaireService.saveQuestionnaire(questionnairePayload);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getQuestionnaireList(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                  @RequestParam(value = "pageSize", defaultValue = "8") int pageSize) {
        return ResponseEntity.ok(questionnaireService.getPagedQuestionnaires(pageIndex, pageSize));
    }

    @GetMapping("/{questionnaireId}")
    public ResponseEntity<?> questionnaireById(@PathVariable Long questionnaireId) {
        return ResponseEntity.ok(questionnaireService.getQuestionnaireById(questionnaireId));
    }


    @PutMapping("/{questionnaireId}")
    public ResponseEntity<?> editQuestionnaire(@PathVariable Long questionnaireId,
                                               @Valid @RequestBody QuestionnairePayload questionnairePayload) {

        questionnaireService.updateQuestionnaire(questionnaireId, questionnairePayload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{questionnaireId}")
    public ResponseEntity<Void> deleteQuestionnaire(@PathVariable Long questionnaireId) {
        questionnaireService.deleteQuestionnaire(questionnaireId);
        return ResponseEntity.ok().build();
    }
}