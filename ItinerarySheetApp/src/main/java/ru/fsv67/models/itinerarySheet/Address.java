package ru.fsv67.models.itinerarySheet;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class Address {
    private Long id;
    private String departureAddress;
    private String destinationAddress;

    public Address(String departureAddress, String destinationAddress) {
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
    }
}
