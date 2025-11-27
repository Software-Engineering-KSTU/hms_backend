package org.example.backendjava.booking_to_doctore_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "current_patient_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPatientStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String symptomsDescribedByPatient;

    @Column(columnDefinition = "TEXT")
    private String selfTreatmentMethodsTaken;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
