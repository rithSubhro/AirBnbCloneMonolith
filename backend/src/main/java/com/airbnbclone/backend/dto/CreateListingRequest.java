package com.airbnbclone.backend.dto;

import java.util.UUID;

public class CreateListingRequest {
    public UUID hostId;
    public String title;
    public String description;
    public String address;
    public double latitude;
    public double longitude;
    public int pricePerNight;
}