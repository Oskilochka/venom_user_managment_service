package com.josk.venom.user.management.mapper;

import com.josk.venom.user.management.dto.RegisterRequestDto;
import com.josk.venom.user.management.dto.UserDto;
import com.josk.venom.user.management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequestDto registerRequestDto);
}
