package com.airbnbclone.backend.repository;

import com.airbnbclone.backend.entity.ListingPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ListingPhotoRepository extends JpaRepository<ListingPhoto, UUID> {
    List<ListingPhoto> findByListingId(UUID listingId);
}
