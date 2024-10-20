package com.overmind.overmind_chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "diagnosis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;

    private String predictedDisease;

    @CreatedDate
    @Column(name = "diagnosis_date")
    private Timestamp diagnosisDate;
}
