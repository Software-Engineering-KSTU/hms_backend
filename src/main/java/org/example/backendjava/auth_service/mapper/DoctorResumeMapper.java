package org.example.backendjava.auth_service.mapper;

import org.example.backendjava.auth_service.model.dto.DoctorResumeRequestDto;
import org.example.backendjava.auth_service.model.dto.DoctorResumeResponseDto;
import org.example.backendjava.auth_service.model.entity.DoctorResume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorResumeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "photoUrl", ignore = true)
    DoctorResume toEntity(DoctorResumeRequestDto dto);

    @Mapping(source = "doctor.id", target = "doctorId")
    DoctorResumeResponseDto toDto(DoctorResume entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "photoUrl", ignore = true)
    void update(@MappingTarget DoctorResume entity, DoctorResumeRequestDto dto);
}
