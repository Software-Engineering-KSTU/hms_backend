package org.example.backendjava.auth_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String specialization;
    private String phoneNumber;
    private LocalDate birthDate;

    /**
     * Каскадное удаление: при удалении врача удаляется его резюме.
     */
    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private DoctorResume resume;

    /**
     * Каскадное удаление: при удалении врача удаляются все его записи с пациентами.
     */
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<org.example.backendjava.booking_to_doctore_service.model.entity.Appointment> appointments = new ArrayList<>();

    /**
     * Каскадное удаление: при удалении врача удаляются все его визиты.
     */
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<org.example.backendjava.doctor_cabinet_service.model.entity.Visit> visits = new ArrayList<>();
}