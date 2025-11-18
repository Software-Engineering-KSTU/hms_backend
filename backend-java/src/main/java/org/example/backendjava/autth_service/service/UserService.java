package org.example.backendjava.autth_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.autth_service.mapper.UserMapper;
import org.example.backendjava.autth_service.model.dto.UserResponseDto;
import org.example.backendjava.autth_service.model.entity.User;
import org.example.backendjava.autth_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с пользователями.
 * Предоставляет методы для поиска, обновления и получения списка пользователей.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Находит пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return объект UserResponseDto с информацией о пользователе
     * @throws UsernameNotFoundException если пользователь с таким ID не найден
     */
    public UserResponseDto findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список объектов UserResponseDto
     */
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public Long findUserIdByUsername(String username) {
        return userRepository.findIdByUsername(username);
    }

}
