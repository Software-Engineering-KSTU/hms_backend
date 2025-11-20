package org.example.backendjava.doctor_cabinet_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.doctor_cabinet_service.model.dto.DepartmentRequestDto;
import org.example.backendjava.doctor_cabinet_service.model.dto.DepartmentResponseDto;
import org.example.backendjava.doctor_cabinet_service.model.entity.Department;
import org.example.backendjava.doctor_cabinet_service.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentResponseDto create(DepartmentRequestDto dto) {
        Department department = new Department();
        department.setName(dto.name());
        Department saved = repository.save(department);
        return new DepartmentResponseDto(saved.getId(), saved.getName());
    }

    public List<DepartmentResponseDto> findAll() {
        return repository.findAll().stream()
                .map(dep -> new DepartmentResponseDto(dep.getId(), dep.getName()))
                .toList();
    }

    public DepartmentResponseDto findById(Long id) {
        Department dep = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return new DepartmentResponseDto(dep.getId(), dep.getName());
    }

    public DepartmentResponseDto update(Long id, DepartmentRequestDto dto) {
        Department dep = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        dep.setName(dto.name());
        Department saved = repository.save(dep);
        return new DepartmentResponseDto(saved.getId(), saved.getName());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
