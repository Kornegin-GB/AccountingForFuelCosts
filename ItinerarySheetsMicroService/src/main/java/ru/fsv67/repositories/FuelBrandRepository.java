package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.fuel.FuelBrandEntity;

@Repository
public interface FuelBrandRepository extends JpaRepository<FuelBrandEntity, Long> {
}
