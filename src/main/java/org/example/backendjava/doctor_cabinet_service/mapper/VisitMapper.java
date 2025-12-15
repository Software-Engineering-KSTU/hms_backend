package org.example.backendjava.doctor_cabinet_service.mapper;

import org.example.backendjava.doctor_cabinet_service.model.dto.VisitRequestDTO;
import org.example.backendjava.doctor_cabinet_service.model.dto.VisitResponseDTO;
import org.example.backendjava.doctor_cabinet_service.model.entity.Visit;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    @Mapping(target = "doctor.id", source = "doctorId")
    @Mapping(target = "patient.id", source = "patientId")
    Visit toEntity(VisitRequestDTO dto);

    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "patientId", source = "patient.id")
    VisitResponseDTO toDto(Visit visit);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Visit visit, VisitRequestDTO dto);
}
