package com.airbnbclone.backend.repository;

import com.airbnbclone.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Date;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    // Get all bookings for a listing
    List<Booking> findByListingId(UUID listingId);

    // Find overlapping bookings (for conflict detection)
    @Query("""
        SELECT b FROM Booking b
        WHERE b.listingId = :listingId
        AND b.status <> 'CANCELLED'
        AND NOT (b.endDate < :start OR b.startDate > :end)
    """)
    List<Booking> findOverlapping(
            @Param("listingId") UUID listingId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    Optional<Booking> findByIdempotencyKey(String idempotencyKey);
}
