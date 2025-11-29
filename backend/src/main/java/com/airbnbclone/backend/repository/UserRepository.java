package com.airbnbclone.backend.repository;

import com.airbnbclone.backend.entity.User;
import com.airbnbclone.backend.entity.Listing;
import com.airbnbclone.backend.entity.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, UUID> {
    // find by email if needed
    java.util.Optional<User> findByEmail(String email);
}
