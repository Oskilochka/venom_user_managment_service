package com.josk.venom.user.management.service;

import com.josk.venom.user.management.dto.UserCreationDto;
import com.josk.venom.user.management.dto.UserDto;
import com.josk.venom.user.management.dto.UserPasswordDto;

import java.util.List;

public interface UserService {
   List<UserDto> getAllUsers();
   UserDto getUserById(Long id);
   UserDto createUser(UserCreationDto userCreationDto);
   UserDto updateUser(Long id, UserDto userDto);
   void deleteUser(Long id);
   void updatePassword(Long id, UserPasswordDto userPasswordDto);
}
