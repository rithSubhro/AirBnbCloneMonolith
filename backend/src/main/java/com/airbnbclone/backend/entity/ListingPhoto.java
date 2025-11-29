package com.airbnbclone.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ListingPhoto {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "listing_id")
    private UUID listingId;   // IMPORTANT — new schema

    private String url;

    private int orderIndex;
}
