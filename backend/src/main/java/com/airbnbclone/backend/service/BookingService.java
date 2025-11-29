package com.airbnbclone.backend.service;

import com.airbnbclone.backend.dto.*;
import com.airbnbclone.backend.entity.*;
import com.airbnbclone.backend.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingDto createBooking(CreateBookingRequest req) {

        // 1. Idempotency key - reuse if exists
        if (req.idempotencyKey != null) {
            Optional<Booking> existing = bookingRepository.findByIdempotencyKey(req.idempotencyKey);
            if (existing.isPresent()) {
                return toDto(existing.get());
            }
        }

        // 2. Listing exists?
        Listing listing = listingRepository.findById(req.listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        // 3. Guest exists?
        User guest = userRepository.findById(req.guestId)
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        // 4. Overlap check
        List<Booking> overlaps = bookingRepository.findOverlapping(
                req.listingId,
                req.startDate,
                req.endDate
        );

        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Selected dates are not available");
        }

        // 5. Calculate price
        long days = Duration.between(
                req.startDate.atStartOfDay(),
                req.endDate.plusDays(1).atStartOfDay()
        ).toDays();

        int totalPrice = (int) (days * listing.getPricePerNight());

        // 6. Create booking
        Booking b = new Booking();
        b.setListingId(listing.getId());
        b.setGuestId(guest.getId());
        b.setStartDate(req.startDate);
        b.setEndDate(req.endDate);
        b.setTotalPrice(totalPrice);
        b.setStatus("PENDING");
        b.setIdempotencyKey(req.idempotencyKey);
        b.setCreatedAt(Instant.now());
        b.setUpdatedAt(Instant.now());

        Booking saved = bookingRepository.save(b);
        return toDto(saved);
    }

    public BookingDto toDto(Booking b) {
        BookingDto dto = new BookingDto();
        dto.id = b.getId();
        dto.listingId = b.getListingId();
        dto.guestId = b.getGuestId();
        dto.startDate = b.getStartDate();
        dto.endDate = b.getEndDate();
        dto.totalPrice = b.getTotalPrice();
        dto.status = b.getStatus();
        dto.paymentId = b.getPaymentId();
        return dto;
    }
    public boolean isAvailable(UUID listingId, LocalDate start, LocalDate end) {
        return bookingRepository.findOverlapping(listingId, start, end).isEmpty();
    }

    @Transactional
    public void markPaid(String bookingId, String paymentId) {

        Booking b = bookingRepository.findById(UUID.fromString(bookingId))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        b.setPaymentId(paymentId);
        b.setStatus("CONFIRMED");
        b.setUpdatedAt(Instant.now());
        bookingRepository.save(b);
    }



}
