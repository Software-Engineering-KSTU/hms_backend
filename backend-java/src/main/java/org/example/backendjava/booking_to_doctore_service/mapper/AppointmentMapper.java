package org.example.backendjava.booking_to_doctore_service.mapper;

import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorAppiontmentResponseDto;
import org.example.backendjava.booking_to_doctore_service.model.dto.SlotDto;
import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "patient.id", target = "patient.id")
    @Mapping(source = "patient.user.username", target = "patient.username")
    @Mapping(source = "patient.phoneNumber", target = "patient.phoneNumber")
    @Mapping(source = "patient.address", target = "patient.address")
    @Mapping(source = "patient.birthDate", target = "patient.birthDate")
    @Mapping(source = "currentPatientStatus.status", target = "status")
    @Mapping(source = "currentPatientStatus.symptomsDescribedByPatient", target = "symptomsDescribedByPatient")
    @Mapping(source = "currentPatientStatus.selfTreatmentMethodsTaken", target = "selfTreatmentMethodsTaken")
    DoctorAppiontmentResponseDto toDto(Appointment appointment);


    SlotDto toSlotDto(Appointment appointment);
}
