package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.DriversLicense;

@Repository
public interface DriversLicenseRepository extends JpaRepository<DriversLicense, Long> {
    DriversLicense findByNumberDriversLicense(String numberDriversLicense);
}
