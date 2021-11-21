package com.example.survey.dao.repository;

import com.example.survey.dao.entity.QuestionnaireEntity;
import com.example.survey.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity,Long> {
    @Query("from QuestionnaireEntity q where q.status='ACTIVE' and q.isMandatory=false")
    Page<QuestionnaireEntity> findAllQuestionnaires(Pageable pageable);
    Optional<QuestionnaireEntity> findByIdAndStatus(Long id, Status status);

    @Query("from QuestionnaireEntity q where q.scheduledAt<=CURRENT_TIMESTAMP and q.status='ACTIVE'")
    Page<QuestionnaireEntity> findByState(Pageable pageable);
}
