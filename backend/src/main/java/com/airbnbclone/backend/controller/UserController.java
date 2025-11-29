package com.airbnbclone.backend.controller;

import com.airbnbclone.backend.dto.CreateUserRequest;
import com.airbnbclone.backend.dto.UserDto;
import com.airbnbclone.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@RequestBody CreateUserRequest req) {
        return userService.createUser(req);
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable UUID id) {
        return userService.findById(id).orElseThrow();
    }
}
