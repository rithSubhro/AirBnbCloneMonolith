package com.airbnbclone.backend.service;

import com.airbnbclone.backend.entity.User;
import com.airbnbclone.backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.airbnbclone.backend.dto.*;

import lombok.RequiredArgsConstructor;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(CreateUserRequest req) {

        User u = new User();
        u.setName(req.name);
        u.setEmail(req.email);
        u.setPhone(req.phone);
        u.setPasswordHash(passwordEncoder.encode(req.password));

        User saved = userRepository.save(u);

        return toDto(saved);
    }

    public Optional<UserDto> findById(UUID id) {
        return userRepository.findById(id).map(this::toDto);
    }

    private UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.id = u.getId();
        dto.name = u.getName();
        dto.email = u.getEmail();
        dto.phone = u.getPhone();
        return dto;
    }
}
