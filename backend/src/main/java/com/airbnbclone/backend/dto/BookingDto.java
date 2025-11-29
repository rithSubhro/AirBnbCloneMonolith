package com.airbnbclone.backend.dto;

import java.time.LocalDate;
import java.util.UUID;

public class BookingDto {
    public UUID id;
    public UUID listingId;
    public UUID guestId;
    public LocalDate startDate;
    public LocalDate endDate;
    public int totalPrice;
    public String status;
    public String paymentId;
    public String idempotencyKey;
}
