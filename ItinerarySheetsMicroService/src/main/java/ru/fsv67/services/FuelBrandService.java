package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.fsv67.models.fuel.FuelBrandEntity;
import ru.fsv67.repositories.FuelBrandRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FuelBrandService {
    private final FuelBrandRepository fuelBrandRepository;

    /**
     * Получение всех брендов топлива из БД
     *
     * @return Список брендов топлива
     */
    public List<FuelBrandEntity> findFuelBrandAll() {
        List<FuelBrandEntity> fuelBrandEntityList = fuelBrandRepository.findAll();
        if (fuelBrandEntityList.isEmpty()) {
            throw new NoSuchElementException("Список брендов топлива пуст");
        }
        return fuelBrandEntityList;
    }

    /**
     * Получение бренда топлива по идентификатору
     *
     * @param id идентификатор бренда топлива
     * @return Бренд топлива по идентификатору
     */
    public FuelBrandEntity findFuelBrandById(Long id) {
        return fuelBrandRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Бренд с Id = " + id + " не найден")
        );
    }

    /**
     * Запись бренда топлива в базу данных
     *
     * @param fuelBrandEntity данные бренда топлива
     * @return Сохраненный бренд топлива
     */
    public FuelBrandEntity saveFuelBrand(FuelBrandEntity fuelBrandEntity) {
        if (fuelBrandEntity.getFuelBrandName().isEmpty()) {
            throw new IllegalArgumentException("Название бренда не задано");
        }
        return fuelBrandRepository.save(fuelBrandEntity);
    }

    /**
     * Удаление бренда из БД по идентификатору
     *
     * @param id идентификатор бренда топлива
     * @return Удаленный бренд топлива
     * @throws NoSuchElementException ошибка, если бренд по заданному идентификатору не найден
     */
    public FuelBrandEntity deleteFuelBrand(Long id) throws NoSuchElementException {
        FuelBrandEntity fuelBrand = findFuelBrandById(id);
        try {
            fuelBrandRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Бренд с id = " + id + " используется. Удаление не возможно");
        }
        return fuelBrand;
    }

    /**
     * Обновление бренда топлива
     *
     * @param fuelBrandEntity изменяемые данные топлива
     * @return Измененные данные топлива
     * @throws IllegalArgumentException ошибка, если название бренда не задано
     * @throws NoSuchElementException   ошибка, если идентификатор изменяемого топлива не найден
     */
    public FuelBrandEntity updateFuelBrand(FuelBrandEntity fuelBrandEntity) throws IllegalArgumentException, NoSuchElementException {
        findFuelBrandById(fuelBrandEntity.getId());
        saveFuelBrand(fuelBrandEntity);
        return fuelBrandEntity;
    }
}
