package ru.fsv67;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fsv67.models.Driver;
import ru.fsv67.models.DriversLicense;
import ru.fsv67.repositories.DriverRepository;
import ru.fsv67.repositories.DriversLicenseRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateTestData {
    private final DriverRepository driverRepository;
    private final DriversLicenseRepository driversLicenseRepository;

    @PostConstruct
    void generateData() {
        DriversLicense driversLicense_1 = new DriversLicense(
                "1111 111111",
                "B, C, C1, D",
                LocalDate.of(2020, 10, 10),
                LocalDate.of(2030, 10, 10),
                true
        );
        DriversLicense driversLicense_2 = new DriversLicense(
                "2222 222222",
                "B, C, C1, D",
                LocalDate.of(2015, 10, 10),
                LocalDate.of(2025, 10, 10),
                true
        );
        DriversLicense driversLicense_3 = new DriversLicense(
                "3333 333333",
                "B, C, C1, D",
                LocalDate.of(2008, 10, 10),
                LocalDate.of(2018, 10, 10),
                false
        );
        DriversLicense driversLicense_4 = new DriversLicense(
                "4444 444444",
                "B, C, C1, D",
                LocalDate.of(2018, 10, 10),
                LocalDate.of(2028, 10, 10),
                true
        );
        driversLicenseRepository.saveAll(
                List.of(driversLicense_1, driversLicense_2, driversLicense_3, driversLicense_4)
        );

        Driver driver_1 = new Driver(
                "Иван",
                "Иванов",
                "Иванович",
                "11111111",
                List.of(driversLicense_1)
        );
        Driver driver_2 = new Driver(
                "Пётр",
                "Петров",
                "Петрович",
                "22222222",
                List.of(driversLicense_2)
        );
        Driver driver_3 = new Driver(
                "Николай",
                "Николаев",
                "Николаевич",
                "33333333",
                List.of(driversLicense_3, driversLicense_4)
        );
        driverRepository.saveAll(List.of(driver_1, driver_2, driver_3));
    }
}