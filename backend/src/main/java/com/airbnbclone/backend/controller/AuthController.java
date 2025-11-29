package com.airbnbclone.backend.controller;

import com.airbnbclone.backend.dto.CreateUserRequest;
import com.airbnbclone.backend.dto.UserDto;
import com.airbnbclone.backend.dto.LoginRequest;
import com.airbnbclone.backend.dto.AuthResponse;
import com.airbnbclone.backend.entity.User;
import com.airbnbclone.backend.repository.UserRepository;
import com.airbnbclone.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequest req) {
        if (userRepository.findByEmail(req.email).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        User u = new User();
        u.setName(req.name);
        u.setEmail(req.email);
        u.setPhone(req.phone);
        u.setPasswordHash(passwordEncoder.encode(req.password));
        User saved = userRepository.save(u);
        UserDto dto = new UserDto();
        dto.id = saved.getId();
        dto.name = saved.getName();
        dto.email = saved.getEmail();
        dto.phone = saved.getPhone();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        // authenticate (will check password)
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email, req.password));
        User u = userRepository.findByEmail(req.email).orElseThrow();
        String token = jwtUtil.generateToken(u.getId(), u.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
