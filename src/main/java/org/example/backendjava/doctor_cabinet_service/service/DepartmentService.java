package org.example.backendjava.doctor_cabinet_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.doctor_cabinet_service.exception.DepartmentAlreadyExistsException;
import org.example.backendjava.doctor_cabinet_service.exception.DepartmentNotFoundException;
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
        // Проверка на существование отделения с таким именем
        if (repository.findByName(dto.name()).isPresent()) {
            throw new DepartmentAlreadyExistsException("Department with name '" + dto.name() + "' already exists");
        }

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
                .orElseThrow(() -> new DepartmentNotFoundException("Department with id " + id + " not found"));
        return new DepartmentResponseDto(dep.getId(), dep.getName());
    }

    public DepartmentResponseDto update(Long id, DepartmentRequestDto dto) {
        Department dep = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with id " + id + " not found"));

        // Проверка при обновлении: если меняем имя, проверяем, не занято ли оно другим отделением
        if (!dep.getName().equals(dto.name()) && repository.findByName(dto.name()).isPresent()) {
            throw new DepartmentAlreadyExistsException("Department with name '" + dto.name() + "' already exists");
        }

        dep.setName(dto.name());
        Department saved = repository.save(dep);
        return new DepartmentResponseDto(saved.getId(), saved.getName());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}