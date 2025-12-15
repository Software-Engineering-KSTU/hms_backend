package org.example.backendjava.doctor_cabinet_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.example.backendjava.auth_service.repository.PatientRepository;
import org.example.backendjava.doctor_cabinet_service.mapper.VisitMapper;
import org.example.backendjava.doctor_cabinet_service.model.dto.VisitRequestDTO;
import org.example.backendjava.doctor_cabinet_service.model.dto.VisitResponseDTO;
import org.example.backendjava.doctor_cabinet_service.model.entity.Visit;
import org.example.backendjava.doctor_cabinet_service.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper mapper;

    public VisitResponseDTO create(VisitRequestDTO dto) {
        doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Visit visit = mapper.toEntity(dto);
        return mapper.toDto(visitRepository.save(visit));
    }

    public VisitResponseDTO update(Long id, VisitRequestDTO dto) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        mapper.update(visit, dto);
        return mapper.toDto(visitRepository.save(visit));
    }

    public VisitResponseDTO get(Long id) {
        return visitRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
    }

    public List<VisitResponseDTO> getByDoctor(Long doctorId) {
        return visitRepository.findAllByDoctor_Id(doctorId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<VisitResponseDTO> getByPatient(Long patientId) {
        return visitRepository.findAllByPatient_Id(patientId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public void delete(Long id) {
        visitRepository.deleteById(id);
    }
}
