package com.josk.venom.user.management.service;

import com.josk.venom.user.management.dto.UserCreationDto;
import com.josk.venom.user.management.dto.UserDto;
import com.josk.venom.user.management.dto.UserPasswordDto;
import com.josk.venom.user.management.exception.EmailAlreadyExistsException;
import com.josk.venom.user.management.exception.InvalidPasswordException;
import com.josk.venom.user.management.exception.UserNotFoundException;
import com.josk.venom.user.management.exception.UsernameAlreadyExistsException;
import com.josk.venom.user.management.mapper.UserMapper;
import com.josk.venom.user.management.model.User;
import com.josk.venom.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = findExistingUser(id);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDto createUser(UserCreationDto userCreationDto) {
        User user = userMapper.toEntity(userCreationDto);
        boolean isUserExist = userRepository.existsByUsername(user.getUsername());

        if (isUserExist) {
            throw new UsernameAlreadyExistsException("Username already taken.");
        }

        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Transactional
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = findExistingUser(id);

        checkEmailUniqueness(existingUser.getEmail(), userDto.getEmail());
        checkUsernameUniqueness(existingUser.getUsername(), userDto.getUsername());

        existingUser.setEmail(userDto.getEmail());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());

        User savedUser = userRepository.save(existingUser);

        return userMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updatePassword(Long id, UserPasswordDto userPasswordDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (passwordEncoder.matches(userPasswordDto.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("The old password is incorrect");
        }

        String encodedPassword = passwordEncoder.encode(userPasswordDto.getNewPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    private User findExistingUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    private void checkEmailUniqueness(String oldEmail, String newEmail ) {
        if(!oldEmail.equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException("Email already in use: " + newEmail);
        }
    }

    private void checkUsernameUniqueness(String oldUsername, String newUsername ) {
        if(!oldUsername.equals(newUsername) && userRepository.existsByUsername(newUsername)) {
            throw new UsernameAlreadyExistsException("Username is already taken: " + newUsername);
        }
    }
}
