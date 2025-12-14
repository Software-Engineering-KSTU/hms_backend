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

    @OneToOne(optional = false)
    @JoinColumn(name = "doctor_id", nullable = false, unique = true)
    private Doctor doctor;

    private String stage;
    private Integer experienceYears;
    private String education;
    private String certificates;

    @Column(name = "photo_url")
    private String photoUrl;

    private String description;
}
