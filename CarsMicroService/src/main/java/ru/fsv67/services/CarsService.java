package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.dto.CarDTO;
import ru.fsv67.models.CarEntity;
import ru.fsv67.repositories.CarsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Логика работы с автомобилями
 */
@Service
@RequiredArgsConstructor
public class CarsService {
    private final CarsRepository carsRepository;
    private final ModelService modelService;

    /**
     * Метод поиска всех автомобилей в системе
     *
     * @return список автомобилей в системе
     */
    public List<CarDTO> findCarAll() {
        List<CarEntity> carEntityList = carsRepository.findAll();
        if (carEntityList.isEmpty()) {
            throw new NoSuchElementException("Список автомобилей пуст");
        }
        List<CarDTO> carDTOList = mapToDTOList(carEntityList);
        carDTOList.sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        return carDTOList;
    }

    /**
     * Запись нового автомобиля в базу данных
     *
     * @param carDTO записываемые данные по автомобилю
     * @return Записанный автомобиль
     */
    public CarDTO saveCar(CarDTO carDTO, boolean checkedNew) throws NoSuchElementException {
        CarEntity carEntity = mapDtoToCarEntity(carDTO);
        modelService.findModelById(carEntity.getCarModel());
        CarEntity carByRegistrationNumber = carsRepository.findCarEntityByRegistrationNumber(
                carEntity.getRegistrationNumber()
        );
        CarEntity carByVin = carsRepository.findCarEntityByVin(
                carEntity.getVin()
        );
        if (carEntity.getRegistrationNumber().isEmpty() || carEntity.getVin().isEmpty()) {
            throw new IllegalArgumentException("Идентификационные данные автомобиля не заданы");
        }
        if (checkedNew) {
            if (carByRegistrationNumber != null) {
                throw new IllegalArgumentException(
                        "Автомобиль с регистрационным номером "
                                + carEntity.getRegistrationNumber() + " существует");
            }
            if (carByVin != null) {
                throw new IllegalArgumentException(
                        "Автомобиль с VIN номером "
                                + carEntity.getVin() + " существует");
            }
        } else {
            if (carEntity.getId() == null) {
                throw new IllegalArgumentException(
                        "Не указан идентификатор автомобиля");
            }

        }
        carsRepository.save(carEntity);
        return mapToDto(carEntity);
    }

    /**
     * Метод поиска автомобиля по идентификатору
     *
     * @param id идентификатор автомобиля
     * @return Найденный автомобиль
     */
    public CarDTO findCarById(Long id) {
        CarEntity carEntity = carsRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Автомобиль с ID = " + id + " не найден")
                );
        return mapToDto(carEntity);
    }

    /**
     * Метод удаления автомобиля из системы
     *
     * @param id идентификатор удаляемого автомобиля
     * @return Удаленный автомобиль
     * @throws NoSuchElementException ошибка, если идентификатор автомобиля не найден
     */
    public CarDTO deleteCarById(Long id) throws NoSuchElementException {
        CarDTO CarDTO = findCarById(id);
        carsRepository.deleteById(id);
        return CarDTO;
    }

    /**
     * Метод изменения данных автомобиля
     *
     * @param carDTO изменяемые данные автомобиля
     * @return Измененный автомобиль
     * @throws NoSuchElementException   ошибка, если изменяемый автомобиля не существует в БД
     * @throws IllegalArgumentException ошибка, если изменяемые данные пустые
     */
    public CarDTO updateCarById(CarDTO carDTO) throws NoSuchElementException, IllegalArgumentException {
        if (carDTO.getId() == null) {
            throw new IllegalArgumentException("Не указан id обновляемой марки топлива");
        }
        findCarById(carDTO.getId());
        return saveCar(carDTO, false);
    }

    private CarDTO mapToDto(CarEntity carEntity) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(carEntity.getId());
        carDTO.setRegistrationNumber(carEntity.getRegistrationNumber());
        carDTO.setVin(carEntity.getVin());
        carDTO.setCarModel(modelService.findModelById(carEntity.getCarModel()));
        carDTO.setFuelConsumptionRate(carEntity.getFuelConsumptionRate());
        carDTO.setSeriesItinerarySheet(carEntity.getSeriesItinerarySheet());
        carDTO.setInitialOdometer(carEntity.getInitialOdometer());
        return carDTO;
    }

    private List<CarDTO> mapToDTOList(List<CarEntity> carEntityList) {
        List<CarDTO> carDTOList = new ArrayList<>();
        for (CarEntity carEntity : carEntityList) {
            carDTOList.add(mapToDto(carEntity));
        }
        return carDTOList;
    }

    private CarEntity mapDtoToCarEntity(CarDTO carDTO) {
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carDTO.getId());
        carEntity.setRegistrationNumber(carDTO.getRegistrationNumber());
        carEntity.setVin(carDTO.getVin());
        carEntity.setCarModel(carDTO.getCarModel().getId());
        carEntity.setFuelConsumptionRate(carDTO.getFuelConsumptionRate());
        carEntity.setSeriesItinerarySheet(carDTO.getSeriesItinerarySheet());
        carEntity.setInitialOdometer(carDTO.getInitialOdometer());
        return carEntity;
    }
}
