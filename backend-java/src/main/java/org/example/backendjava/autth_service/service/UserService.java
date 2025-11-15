package org.example.backendjava.autth_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.autth_service.dto.UserRequestDto;
import org.example.backendjava.autth_service.dto.UserResponseDto;
import org.example.backendjava.autth_service.entity.User;
import org.example.backendjava.autth_service.mapper.UserMapper;
import org.example.backendjava.autth_service.repository.UserRepository;
import org.example.backendjava.autth_service.userexception.EmailAlreadyExistsException;
import org.example.backendjava.autth_service.userexception.UsernameAlreadyExistsException;
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

    /**
     * Обновляет данные пользователя.
     *
     * @param id идентификатор пользователя для обновления
     * @param updatedUser объект UserRequestDto с новыми данными
     * @return обновленный объект UserResponseDto
     * @throws UsernameAlreadyExistsException если новое имя пользователя уже занято
     * @throws EmailAlreadyExistsException если новый email уже используется
     * @throws UsernameNotFoundException если пользователь с указанным ID не найден
     */
    public UserResponseDto updateUser(Long id, UserRequestDto updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        if (userRepository.findByUsername(updatedUser.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("User with name: " + updatedUser.getUsername() + " already exists");
        }

        if (userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("User with email: " + updatedUser.getUsername() + " already exists");
        }
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        return userMapper.toDto(userRepository.save(existingUser));
    }
}
