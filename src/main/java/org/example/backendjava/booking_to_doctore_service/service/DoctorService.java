package org.example.backendjava.booking_to_doctore_service.service;

import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.example.backendjava.booking_to_doctore_service.DoctorMapper;
import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {

    public List<DoctorResponseDto> findAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .toList();
    }

}
