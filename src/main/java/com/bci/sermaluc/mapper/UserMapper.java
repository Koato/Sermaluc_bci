package com.bci.sermaluc.mapper;

import com.bci.sermaluc.dto.UserRequestDTO;
import com.bci.sermaluc.entity.UserEntity;
import org.mapstruct.*;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true) // Ignorar el ID porque será autogenerado
    @Mapping(target = "created", expression = "java(LocalDateTime.now())")
    @Mapping(target = "modified", expression = "java(LocalDateTime.now())")
    @Mapping(target = "lastLogin", expression = "java(LocalDateTime.now())")
    @Mapping(target = "token", constant = "token") // Reemplazar con un valor temporal
    @Mapping(target = "password", constant = "user.setPassword(passwordEncoder.encode(userRequest.getPassword()))")
    @Mapping(target = "isActive", constant = "true")
    UserEntity toEntity(UserRequestDTO dto);
}
