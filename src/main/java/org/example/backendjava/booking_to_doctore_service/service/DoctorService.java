package org.example.backendjava.booking_to_doctore_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.example.backendjava.booking_to_doctore_service.DoctorMapper;
import org.example.backendjava.booking_to_doctore_service.model.dto.DoctorResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public List<DoctorResponseDto> findAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    public DoctorResponseDto findDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("doctor not found"));
        return doctorMapper.toDto(doctor);
    }

}
