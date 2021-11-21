package com.example.survey.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "options")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "options_seq", allocationSize = 1)
public class OptionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private Long id;

    private String content;
    private int nextQuestionOrderId;

    @ManyToOne
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    @JsonIgnore
    private QuestionEntity questionEntity;
}

