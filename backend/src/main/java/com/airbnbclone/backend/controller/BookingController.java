package com.airbnbclone.backend.controller;

import com.airbnbclone.backend.dto.*;
import com.airbnbclone.backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestBody CreateBookingRequest req) {
        return bookingService.createBooking(req);
    }


    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam UUID listingId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok(bookingService.isAvailable(listingId, start, end));
    }

}
