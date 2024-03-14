package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.models.Driver;
import ru.fsv67.repositories.DriverRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис управления водителями
 */
@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
//    private final DriversLicenseService driversLicenseService;

    /**
     * Метод получения списка водителей из БД
     *
     * @return Список водителей
     */
    public List<Driver> findDriverAll() {
        List<ru.fsv67.models.Driver> driverList = driverRepository.findAll();
        if (driverList.isEmpty()) {
            throw new NoSuchElementException("Список водителей пуст");
        }
        driverList.sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        return driverList;
    }

    /**
     * Метод получения водителя по идентификатору
     *
     * @param id идентификатор водителя
     * @return искомый водитель
     */
    public Driver findDriverById(Long id) {
        return driverRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(
                        "Водитель с ID = " + id + " не найдено")
        );
    }

    /**
     * Метод записи водителя в БД
     *
     * @param newDriver данные водителя для записи
     * @return записанные данные водителя
     */
    public Driver saveDriver(Driver newDriver, boolean isNew) {
        if (isNew) {
            Driver driver = driverRepository.findByFirstNameOrLastNameOrSnils(
                    newDriver.getFirstName(),
                    newDriver.getLastName(),
                    newDriver.getSnils()
            );
            if (driver != null) {
                throw new IllegalArgumentException(
                        "Водитель " + driver.getFirstName()
                                + " " + driver.getLastName() + " существует");
            }
        }
        if (newDriver.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Имя водителя не задано");
        }
        if (newDriver.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Фамилия водителя не задана");
        }
        if (newDriver.getSnils().isEmpty()) {
            throw new IllegalArgumentException("СНИЛС водителя не задан");
        }
//        if (driverDTO.getDriversLicense().getNumberDriversLicense().isEmpty()) {
//            throw new IllegalArgumentException("Водительское удостоверение водителя не задано");
//        }
        return driverRepository.save(newDriver);
    }

    /**
     * Метод удаления водителя из БД
     *
     * @param id идентификатор водителя
     * @return удаленный водитель
     * @throws NoSuchElementException ошибка, если водителя нет в БД
     */
    public Driver deleteDriver(Long id) throws NoSuchElementException {
        Driver driver = findDriverById(id);
        driverRepository.deleteById(id);
        return driver;
    }

    /**
     * Метод изменения данных водителя
     *
     * @param driver измененные данные водителя
     * @return измененный водитель
     * @throws IllegalArgumentException ошибка, если не все данные внесены
     * @throws NoSuchElementException   ошибка, если водитель для изменения не найден
     */
    public Driver updateDriver(Driver driver) throws IllegalArgumentException, NoSuchElementException {
        if (driver.getId() == null) {
            throw new IllegalArgumentException("Не указан id обновляемой марки топлива");
        }
        findDriverById(driver.getId());
        return saveDriver(driver, false);
    }
}
