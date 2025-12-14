package org.example.backendjava.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.dto.UserResponseDto;
import org.example.backendjava.auth_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> allUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @Operation(summary = "Найти ID пользователя по username")
    @GetMapping("/find-id/{username}")
    public ResponseEntity<Long> findIdByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserIdByUsername(username));
    }
}
