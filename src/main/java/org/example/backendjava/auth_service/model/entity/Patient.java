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
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String phoneNumber;
    private String address;
    private LocalDate birthDate;

    /**
     * Каскадное удаление: при удалении пациента удаляются все его записи к врачам.
     */
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<org.example.backendjava.booking_to_doctore_service.model.entity.Appointment> appointments = new ArrayList<>();

    /**
     * Каскадное удаление: при удалении пациента удаляются все его визиты.
     */
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<org.example.backendjava.doctor_cabinet_service.model.entity.Visit> visits = new ArrayList<>();
}