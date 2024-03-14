package ru.fsv67.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "snils")
    private String snils;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "driver_id")
    private List<DriversLicense> driversLicense;

    public Driver(String firstName, String lastName, String parentName, String snils, List<DriversLicense> driversLicense) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentName = parentName;
        this.snils = snils;
        this.driversLicense = driversLicense;
    }
}
