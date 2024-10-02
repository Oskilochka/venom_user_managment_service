package com.josk.venom.user.management.mapper;

import com.josk.venom.user.management.dto.UserCreationDto;
import com.josk.venom.user.management.dto.UserDto;
import com.josk.venom.user.management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserCreationDto userCreationDto);
}
