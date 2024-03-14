package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.models.CarBrand;
import ru.fsv67.repositories.CarBrandRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Логика работы с брендами автомобиля
 */
@Service
@RequiredArgsConstructor
public class BrandService {
    private final CarBrandRepository carBrandRepository;

    /**
     * Метод поиска всех брендов автомобиля в системе
     *
     * @return список брендов автомобилей в системе
     */
    public List<CarBrand> findBrandAll() {
        List<CarBrand> carBrandList = carBrandRepository.findAll();
        if (carBrandList.isEmpty()) {
            throw new NoSuchElementException("Список брендов автомобилей пуст");
        }
        carBrandList.sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        return carBrandList;
    }

    /**
     * Метод поиска брендов по идентификатору
     *
     * @param id идентификатор бренда автомобиля
     * @return Бренд автомобиля с искомым идентификатором
     */
    public CarBrand findBrandById(Long id) {
        return carBrandRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Бренд автомобиля с ID = " + id + " не найден")
        );
    }

    /**
     * Метод сохранения бренда автомобиля в БД
     *
     * @param carBrand сохраняемые данные бренда автомобиля
     * @return сохраненный бренд автомобиля
     */
    public CarBrand saveBrand(CarBrand carBrand) {
        CarBrand brand = carBrandRepository.findCarBrandByBrandName(carBrand.getBrandName());
        if (brand != null) {
            throw new IllegalArgumentException(
                    "Бренд автомобиля с названием " + carBrand.getBrandName() + " существует"
            );
        }
        if (carBrand.getBrandName().isEmpty()) {
            throw new IllegalArgumentException("Название бренда автомобиля не указано");
        }
        return carBrandRepository.save(carBrand);
    }

    /**
     * Метод удаления бренда автомобиля из системы
     *
     * @param id идентификатор удаляемого бренда автомобиля
     * @return Удаленный бренд автомобиля
     * @throws NoSuchElementException ошибка если идентификатор бренда автомобиля не найден
     */
    public CarBrand deleteBrandById(Long id) throws NoSuchElementException {
        CarBrand carBrand = findBrandById(id);
        carBrandRepository.deleteById(id);
        return carBrand;
    }

    /**
     * Метод изменения бренда автомобиля
     *
     * @param brand изменяемые данные бренда автомобиля
     * @return Измененный бренд автомобиля
     * @throws NoSuchElementException   ошибка, если изменяемый бренд автомобиля не существует в БД
     * @throws IllegalArgumentException ошибка, если изменяемые данные пустые
     */
    public CarBrand updateBrandById(CarBrand brand) throws NoSuchElementException, IllegalArgumentException {
        if (brand.getId() == null) {
            throw new IllegalArgumentException("Не указан id обновляемой марки топлива");
        }
        findBrandById(brand.getId());
        return saveBrand(brand);
    }
}
