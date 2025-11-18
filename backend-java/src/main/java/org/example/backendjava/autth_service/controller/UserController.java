package org.example.backendjava.autth_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.autth_service.model.dto.UserResponseDto;
import org.example.backendjava.autth_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер, отвечающий за управление пользователями.
 * <p>
 * Содержит эндпоинты для получения всех пользователей, поиска конкретного пользователя и обновления данных пользователя.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Возвращает список всех пользователей.
     *
     * @return список объектов {@link UserResponseDto} с информацией о пользователях
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> allUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект {@link UserResponseDto} с информацией о пользователе
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping("/find-id/{username}")
    public ResponseEntity<Long> findIdByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserIdByUsername(username));
    }
}