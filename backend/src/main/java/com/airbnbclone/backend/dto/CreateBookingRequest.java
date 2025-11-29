package com.airbnbclone.backend.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreateBookingRequest {
    public UUID listingId;
    public UUID guestId;
    public LocalDate startDate;
    public LocalDate endDate;
    public int totalPrice; // front-end computes OR backend computes
    public String idempotencyKey;
}
