package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.CarModel;

/**
 * Интерфейс взаимодействия с БД моделей автомобилей
 */
@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    /**
     * Метод поиска модели автомобиля по имени
     *
     * @param modelName искомое название модели автомобиля
     * @return Модель автомобиля
     */
    CarModel findCarModelByModelName(String modelName);
}
