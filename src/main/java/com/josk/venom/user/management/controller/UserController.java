package com.josk.venom.user.management.controller;

import com.josk.venom.user.management.dto.UserPasswordDto;
import com.josk.venom.user.management.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userService;

    @PatchMapping("/{id}/change-password")
    private ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(id, userPasswordDto);
        return ResponseEntity.ok().build();
    }
}
