package com.airbnbclone.backend.service;

import com.airbnbclone.backend.dto.CreateListingRequest;
import com.airbnbclone.backend.dto.ListingDto;
import com.airbnbclone.backend.dto.ListingPhotoDto;

import com.airbnbclone.backend.entity.Listing;
import com.airbnbclone.backend.entity.ListingPhoto;

import com.airbnbclone.backend.repository.ListingRepository;
import com.airbnbclone.backend.repository.ListingPhotoRepository;
import com.airbnbclone.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingPhotoRepository photoRepository;
    private final UserRepository userRepository;

    // ---------------- create listing ---------------- //
    public ListingDto createListing(CreateListingRequest req) {

        // 1. verify host exists
        userRepository.findById(req.hostId)
                .orElseThrow(() -> new NoSuchElementException("Host not found"));

        // 2. create entity
        Listing l = new Listing();
        l.setId(UUID.randomUUID());  // if @GeneratedValue is present remove this line
        l.setHostId(req.hostId);
        l.setTitle(req.title);
        l.setDescription(req.description);
        l.setAddress(req.address);
        l.setLatitude(req.latitude);
        l.setLongitude(req.longitude);
        l.setPricePerNight(req.pricePerNight);
        l.setCreatedAt(new Date());

        Listing saved = listingRepository.save(l);

        // DTO response
        return toDto(saved, Collections.emptyList());
    }

    // ---------------- get all listings ---------------- //
    public List<ListingDto> getAll() {
        List<Listing> listings = listingRepository.findAll();

        List<ListingPhoto> photos = photoRepository.findAll();

        // group photos by listingId
        Map<UUID, List<ListingPhoto>> photosByListing =
                photos.stream().collect(Collectors.groupingBy(
                        p -> p.getListingId()
                ));

        return listings.stream()
                .map(l -> toDto(l, photosByListing.getOrDefault(l.getId(), List.of())))
                .collect(Collectors.toList());
    }

    // ---------------- get by id ---------------- //
//    public ListingDto getById(UUID id) {
//        Listing l = listingRepository.findById(id).orElseThrow();
//
//        List<ListingPhoto> photos = photoRepository.findByListingId(id);
//
//        return toDto(l, photos);
//    }

    public Optional<ListingDto> getById(UUID id) {
        Listing l = listingRepository.findById(id).orElse(null);
        if (l == null) return Optional.empty();

        List<ListingPhoto> photos = photoRepository.findByListingId(id);

        return Optional.of(toDto(l, photos));
    }


    // ---------------- helper ---------------- //
    private ListingDto toDto(Listing l, List<ListingPhoto> photos) {
        ListingDto dto = new ListingDto();
        dto.id = l.getId();
        dto.hostId = l.getHostId();
        dto.title = l.getTitle();
        dto.description = l.getDescription();
        dto.address = l.getAddress();
        dto.latitude = l.getLatitude();
        dto.longitude = l.getLongitude();
        dto.pricePerNight = l.getPricePerNight();
        dto.createdAt = l.getCreatedAt();

        dto.photos = photos.stream().map(p -> {
            ListingPhotoDto pd = new ListingPhotoDto();
            pd.id = p.getId();
            pd.url = p.getUrl();
            pd.orderIndex = p.getOrderIndex();
            return pd;
        }).collect(Collectors.toList());

        return dto;
    }
}
