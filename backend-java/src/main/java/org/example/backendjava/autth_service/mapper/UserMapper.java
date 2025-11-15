package org.example.backendjava.autth_service.mapper;


import org.example.backendjava.autth_service.dto.UserRequestDto;
import org.example.backendjava.autth_service.dto.UserResponseDto;
import org.example.backendjava.autth_service.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto dto);
}





