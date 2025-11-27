package org.example.backendjava.auth_service.service;

import org.example.backendjava.auth_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для загрузки информации о пользователе по имени пользователя.
 * Используется Spring Security для аутентификации.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    /**
     * Конструктор с внедрением UserRepository.
     *
     * @param repository репозиторий пользователей
     */
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Загружает пользователя по username.
     *
     * @param username имя пользователя
     * @return объект UserDetails, содержащий данные пользователя и его роли
     * @throws UsernameNotFoundException если пользователь с таким username не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + username));
    }
}
