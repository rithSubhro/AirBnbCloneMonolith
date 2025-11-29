package com.airbnbclone.backend.service;

import com.airbnbclone.backend.entity.User;
import com.airbnbclone.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // used by username-based auth (if needed)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return toUserDetails(u);
    }

    // used by JwtAuthenticationFilter
    public UserDetails loadUserById(UUID id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return toUserDetails(u);
    }

    private UserDetails toUserDetails(User u) {
        // simple role: all users have ROLE_USER. extend later.
        return org.springframework.security.core.userdetails.User.withUsername(u.getEmail())
                .password(u.getPasswordHash()) // needed by Spring but not used in JWT validation
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false)
                .build();
    }
}
