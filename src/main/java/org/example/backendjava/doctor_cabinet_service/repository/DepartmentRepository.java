package org.example.backendjava.doctor_cabinet_service.repository;

import org.example.backendjava.doctor_cabinet_service.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
}