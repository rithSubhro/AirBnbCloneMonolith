package com.airbnbclone.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name="booking")
public class Booking {
    @Id @GeneratedValue
    private UUID id;

    @Column(name="listing_id", nullable=false)
    private UUID listingId;

    @Column(name="guest_id", nullable=false)
    private UUID guestId;

    private LocalDate startDate;
    private LocalDate endDate;
    private int totalPrice;

    private String status; // PENDING, CONFIRMED, CANCELLED
    private String idempotencyKey;
    private String paymentId;

    @Version
    private int version;

    private Instant createdAt;
    private Instant updatedAt;
    // getters/setters
}

