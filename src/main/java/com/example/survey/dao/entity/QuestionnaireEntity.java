package com.example.survey.dao.entity;


import com.example.survey.model.enums.QuestionnaireStatus;
import com.example.survey.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questionnaire")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "questionnaire_seq", allocationSize = 1)
public class QuestionnaireEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private Long id;

    private String title;
    private String description;
    @Column(name = "questionnaire_status")
    @Enumerated(EnumType.STRING)
    private QuestionnaireStatus questionnaireStatus;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean isMandatory;
    private LocalDateTime scheduledAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "questionnaireEntity")
    private List<QuestionEntity> questionEntities = new ArrayList<>();

}
