package ru.fsv67.models.itinerarySheet;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class Address {
    private Long id;
    private String address;

    public Address(String address) {
        this.address = address;
    }
}
