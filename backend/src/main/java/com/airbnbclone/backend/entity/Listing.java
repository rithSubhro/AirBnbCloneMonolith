package com.airbnbclone.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Listing {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "host_id")
    private UUID hostId;

    private String title;
    private String description;
    private String address;

    private double latitude;
    private double longitude;

    private int pricePerNight;

    private Date createdAt = new Date();
}
