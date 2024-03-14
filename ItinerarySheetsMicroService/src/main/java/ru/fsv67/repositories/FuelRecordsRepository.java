package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.fuel.FuelRecordEntity;

@Repository
public interface FuelRecordsRepository extends JpaRepository<FuelRecordEntity, Long> {
}
