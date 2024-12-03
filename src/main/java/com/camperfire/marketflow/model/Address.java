package com.camperfire.marketflow.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String street;         // Street name and number
    private String city;           // City name
    private String state;          // State or region
    private String country;        // Country
    private String postCode;       // Postal code or ZIP code

    private String additionalInfo; // Optional: apartment, suite, or other details
}