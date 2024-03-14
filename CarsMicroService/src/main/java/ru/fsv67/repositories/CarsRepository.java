package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.CarEntity;

/**
 * Интерфейс взаимодействия с БД автомобили
 */
@Repository
public interface CarsRepository extends JpaRepository<CarEntity, Long> {
    /**
     * Поиска автомобиля по регистрационному номеру
     *
     * @param registrationNumber регистрационный номер автомобиля
     * @return Автомобиль с искомым регистрационным номером
     */
    CarEntity findCarEntityByRegistrationNumber(String registrationNumber);

    /**
     * Поиск автомобиля по VIN номеру
     *
     * @param vin уникальный идентификационный номер автомобиля
     * @return Автомобиль с искомым VIN номером
     */
    CarEntity findCarEntityByVin(String vin);

}
