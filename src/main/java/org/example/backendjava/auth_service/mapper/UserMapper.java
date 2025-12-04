package org.example.backendjava.auth_service.mapper;


import org.example.backendjava.auth_service.model.dto.UserRequestDto;
import org.example.backendjava.auth_service.model.dto.UserResponseDto;
import org.example.backendjava.auth_service.model.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto dto);
}





