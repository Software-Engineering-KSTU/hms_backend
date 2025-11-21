package org.example.backendjava.booking_to_doctore_service.mapper;


import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorAppiontmentResponseDto;
import org.example.backendjava.booking_to_doctore_service.model.entity.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    DoctorAppiontmentResponseDto toDto(Appointment appointment);
}
