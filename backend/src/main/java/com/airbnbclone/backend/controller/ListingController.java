package com.airbnbclone.backend.controller;

import com.airbnbclone.backend.dto.CreateListingRequest;
import com.airbnbclone.backend.dto.ListingDto;
import com.airbnbclone.backend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @GetMapping
    public List<ListingDto> getAll() {
        return listingService.getAll();
    }

//    @GetMapping("/{id}")
//    public ListingDto get(@PathVariable UUID id) {
//        return listingService.getById(id);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDto> getOne(@PathVariable UUID id) {
        return listingService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ListingDto create(@RequestBody CreateListingRequest req) {
        return listingService.createListing(req);
    }
}
