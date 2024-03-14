package ru.fsv67.models.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    private Long id;
    private String firstName;
    private String lastName;
    private String parentName;
    private String snils;
    private List<DriversLicense> driversLicense;

    public Driver(String firstName, String lastName, String parentName, String snils, List<DriversLicense> driversLicense) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentName = parentName;
        this.snils = snils;
        this.driversLicense = driversLicense;
    }

    public Driver(String firstName, String lastName, String parentName, String snils) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentName = parentName;
        this.snils = snils;
    }
}
