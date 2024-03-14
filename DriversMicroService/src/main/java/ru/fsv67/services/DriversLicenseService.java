package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.models.DriversLicense;
import ru.fsv67.repositories.DriversLicenseRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис управления водительскими удостоверениями
 */
@Service
@RequiredArgsConstructor
public class DriversLicenseService {
    private final DriversLicenseRepository driversLicenseRepository;

    /**
     * Метод получения списка водительских удостоверений из БД
     *
     * @return Список водительских удостоверений
     */
    public List<DriversLicense> findDriversLicenseAll() {
        List<DriversLicense> driversLicenses = driversLicenseRepository.findAll();
        if (driversLicenses.isEmpty()) {
            throw new NoSuchElementException("Список водительских удостоверений пуст");
        }
        driversLicenses.sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        return driversLicenses;
    }

    /**
     * Метод получения водительского удостоверения по идентификатору
     *
     * @param id идентификатор водительского удостоверения
     * @return искомое водительское удостоверение
     */
    public DriversLicense findDriversLicenseById(Long id) {
        return driversLicenseRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(
                        "Водительское удостоверение с ID = " + id + " не найдено")
        );
    }

    /**
     * Метод записи водительского удостоверения в БД
     *
     * @param driversLicense данные удостоверения для записи
     * @return записанное удостоверение
     */
    public DriversLicense saveDriversLicense(DriversLicense driversLicense, boolean isNew) {
        if (isNew) {
            DriversLicense license =
                    driversLicenseRepository.findByNumberDriversLicense(driversLicense.getNumberDriversLicense());
            if (license != null) {
                throw new IllegalArgumentException(
                        "Водительское удостоверение с номером " + license.getNumberDriversLicense() + " существует"
                );
            }
        }
        if (driversLicense.getNumberDriversLicense().isEmpty()) {
            throw new IllegalArgumentException("Номер водительского удостоверения не задан");
        }
        if (driversLicense.getCategories().isEmpty()) {
            throw new IllegalArgumentException("Категории водительского удостоверения не заданы");
        }
        return driversLicenseRepository.save(driversLicense);
    }

    /**
     * Метод удаления водительского удостоверения из БД
     *
     * @param id идентификатор водительского удостоверения
     * @return удаленное водительское удостоверение
     * @throws NoSuchElementException ошибка, если водительского удостоверения нет в БД
     */
    public DriversLicense deleteDriversLicense(Long id) throws NoSuchElementException {
        DriversLicense driversLicense = findDriversLicenseById(id);
        driversLicenseRepository.deleteById(id);
        return driversLicense;
    }

    /**
     * Метод изменения водительского удостоверения
     *
     * @param driversLicense измененные данные водительского удостоверения
     * @return измененное водительское удостоверение
     * @throws IllegalArgumentException ошибка, если не все данные внесены
     * @throws NoSuchElementException   ошибка, если водительское удостоверение для изменения не найдено
     */
    public DriversLicense updateDriversLicense(DriversLicense driversLicense) throws IllegalArgumentException, NoSuchElementException {
        if (driversLicense.getId() == null) {
            throw new IllegalArgumentException("Не указан id обновляемой марки топлива");
        }
        findDriversLicenseById(driversLicense.getId());
        return saveDriversLicense(driversLicense, false);
    }
}
