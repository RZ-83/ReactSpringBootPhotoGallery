package com.photogallery.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String suite;
    private String city;
    private String zipCode;
    private GeoDto geo;
}
