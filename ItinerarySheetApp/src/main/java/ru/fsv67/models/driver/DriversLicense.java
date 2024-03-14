package ru.fsv67.models.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriversLicense {
    private Long id;
    private String numberDriversLicense;
    private String categories;
    private LocalDate dateOfIssue;
    private LocalDate dateOfValidity;
    private Boolean isReally;
    private Long driverId;

    public DriversLicense(String numberDriversLicense, String categories, LocalDate dateOfIssue,
                          LocalDate dateOfValidity, Boolean isReally, Long driverId) {
        this.numberDriversLicense = numberDriversLicense;
        this.categories = categories;
        this.dateOfIssue = dateOfIssue;
        this.dateOfValidity = dateOfValidity;
        this.isReally = isReally;
        this.driverId = driverId;
    }
}
