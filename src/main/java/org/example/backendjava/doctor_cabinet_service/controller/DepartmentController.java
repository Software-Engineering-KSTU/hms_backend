package org.example.backendjava.doctor_cabinet_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.doctor_cabinet_service.model.dto.DepartmentRequestDto;
import org.example.backendjava.doctor_cabinet_service.model.dto.DepartmentResponseDto;
import org.example.backendjava.doctor_cabinet_service.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Tag(name = "Departments")
public class DepartmentController {

    private final DepartmentService service;

    @Operation(summary = "Создать отделение")
    @PostMapping
    public ResponseEntity<DepartmentResponseDto> create(
            @RequestBody DepartmentRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Получить список всех отделений")
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Получить отделение по ID")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Обновить отделение")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> update(
            @PathVariable Long id,
            @RequestBody DepartmentRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Удалить отделение")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
