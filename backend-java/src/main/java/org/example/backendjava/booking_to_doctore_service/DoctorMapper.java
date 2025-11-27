package org.example.backendjava.booking_to_doctore_service;

import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorResponseDto toDto(Doctor doctor);
}
