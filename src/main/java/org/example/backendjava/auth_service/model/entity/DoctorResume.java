package org.example.backendjava.auth_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctor_resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "doctor_id", nullable = false, unique = true)
    private Doctor doctor;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    private Integer experienceYears;

    private Integer age;

    @Column(columnDefinition = "TEXT")
    private String educationJson; // JSON: [{university, year, specialty, diplomaPhotoUrl}]

    // Можно хранить как строку URL или отдельно сущность Photo — пока просто URL
    private String photoUrl;
}