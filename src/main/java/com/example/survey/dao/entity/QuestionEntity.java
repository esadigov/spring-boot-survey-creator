package com.example.survey.dao.entity;


import com.example.survey.model.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "questions_seq", allocationSize = 1)
public class QuestionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private Long id;

    private String content;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionEntity")
    private List<OptionEntity> optionEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "id")
    @JsonIgnore
    private QuestionnaireEntity questionnaireEntity;
}
