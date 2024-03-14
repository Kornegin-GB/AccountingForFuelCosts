package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.CarBrand;

/**
 * Интерфейс взаимодействия с БД бренды автомобиля
 */
@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    /**
     * Метод поиска в БД бренда автомобиля по названию
     *
     * @param brandName искомое название бренда автомобиля
     * @return Бренд автомобиля
     */
    CarBrand findCarBrandByBrandName(String brandName);
}
