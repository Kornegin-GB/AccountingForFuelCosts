package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.ItinerarySheetEntity;

import java.util.List;

@Repository
public interface ItinerarySheetRepository extends JpaRepository<ItinerarySheetEntity, Long> {
    /**
     * Получение количества маршрутных листов по идентификатору автомобиля
     *
     * @param id идентификатор автомобиля
     * @return Количество маршрутных листов
     */
    Integer countByCarId(Long id);

    /**
     * Получение списка маршрутных листов по идентификатору автомобиля
     *
     * @param id идентификатор автомобиля
     * @return Список маршрутных листов
     */
    List<ItinerarySheetEntity> findByCarId(Long id);

    /**
     * Получение последнего маршрутного листа по идентификатору автомобиля
     *
     * @param carId идентификатор автомобиля
     * @return Последний маршрутный лист
     */
    ItinerarySheetEntity findFirstByCarIdOrderByIdDesc(Long carId);
}
