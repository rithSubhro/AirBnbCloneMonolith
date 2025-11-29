package com.airbnbclone.backend.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ListingDto {
    public UUID id;
    public UUID hostId;
    public String title;
    public String description;
    public String address;
    public double latitude;
    public double longitude;
    public int pricePerNight;
    public Date createdAt;

    public List<ListingPhotoDto> photos;
}
