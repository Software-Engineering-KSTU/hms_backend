package org.example.backendjava.auth_service.repository;

import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Находит врача по ID пользователя.
     * Необходимо для получения профиля врача из токена аутентификации.
     *
     * @param userId ID пользователя
     * @return Optional с профилем врача
     */
    Optional<Doctor> findByUserId(Long userId);

    Optional<Doctor> findById(Long id);

    Long user(User user);
}