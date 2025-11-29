package com.airbnbclone.backend.repository;

import com.airbnbclone.backend.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {
    // basic search by host
    List<Listing> findByHostId(UUID hostId);
}
