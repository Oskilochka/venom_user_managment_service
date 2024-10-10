package com.josk.venom.user.management.service.user;

import com.josk.venom.user.management.dto.UpdatePasswordRequestDto;
import com.josk.venom.user.management.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
   List<UserDto> getAllUsers();
   UserDto getUserById(Long id);
   UserDto updateUser(Long id, UserDto userDto);
   void deleteUser(Long id);
   void updatePassword(Long id, UpdatePasswordRequestDto updatePasswordRequestDto);
   void updateLastLogout(String username);
   UserDetailsService userDetailsService();
}
