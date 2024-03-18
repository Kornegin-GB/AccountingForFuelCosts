package ru.fsv67.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "departure_address_name")
    private String departureAddress;
    @Column(name = "destination_address_name")
    private String destinationAddress;

    public AddressEntity(String departureAddress, String destinationAddress) {
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
    }
}
