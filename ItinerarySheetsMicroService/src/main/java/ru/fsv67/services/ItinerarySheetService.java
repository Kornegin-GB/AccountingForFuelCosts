package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.dto.ItinerarySheetDTO;
import ru.fsv67.models.AddressEntity;
import ru.fsv67.models.ItinerarySheetEntity;
import ru.fsv67.models.fuel.FuelRecordEntity;
import ru.fsv67.repositories.AddressRepository;
import ru.fsv67.repositories.FuelRecordsRepository;
import ru.fsv67.repositories.ItinerarySheetRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItinerarySheetService {
    private final ItinerarySheetRepository itinerarySheetRepository;
    private final AddressRepository addressRepository;
    private final FuelRecordsRepository fuelRecordsRepository;
    private final DtoTransfer dtoTransfer;

    /**
     * Поиск всех маршрутных листов
     *
     * @return Список маршрутных листов
     */
    public List<ItinerarySheetDTO> findAllItinerarySheet() {
        List<ItinerarySheetEntity> itinerarySheetEntityList = itinerarySheetRepository.findAll();
        if (itinerarySheetEntityList.isEmpty()) {
            throw new NoSuchElementException("Список маршрутных листов пуст");
        }
        List<ItinerarySheetDTO> itinerarySheetDTOList = dtoTransfer.mapItinerarySheetEntityToDtoList(itinerarySheetEntityList);
        itinerarySheetDTOList.sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        return itinerarySheetDTOList;
    }

    /**
     * Поиск маршрутных листов по идентификатору автомобиля
     *
     * @param id идентификатор автомобиля
     * @return Список маршрутных листов по идентификатору автомобиля
     */
    public List<ItinerarySheetDTO> findByCarId(Long id) {
        List<ItinerarySheetEntity> itinerarySheetEntityList = itinerarySheetRepository.findByCarId(id);
        if (itinerarySheetEntityList.isEmpty()) {
            throw new NoSuchElementException("Список маршрутных листов пуст");
        }
        return dtoTransfer.mapItinerarySheetEntityToDtoList(itinerarySheetEntityList);
    }

    /**
     * Поиск последнего маршрутного листа по идентификатору автомобиля
     *
     * @param id идентификатор автомобиля
     * @return Последний маршрутный лист по идентификатору автомобиля
     */
    public ItinerarySheetDTO findLastItinerarySheetDyCarId(Long id) {
        ItinerarySheetEntity itinerarySheet = itinerarySheetRepository.findFirstByCarIdOrderByIdDesc(id);
        if (itinerarySheet == null) {
            throw new NoSuchElementException("Маршрутные листы для автомобиля с ID = " + id + " не найдены");
        }
        return dtoTransfer.mapItinerarySheetEntityToDto(itinerarySheet);
    }

    /**
     * Запись маршрутного листа в БД
     *
     * @param itinerarySheetDTO сохраняемые данные маршрутного листа
     * @return Сохраненный маршрутный лист
     */
    public ItinerarySheetDTO saveItinerarySheet(ItinerarySheetDTO itinerarySheetDTO, boolean isNew) {
        if (isNew) {
            for (AddressEntity addressEntity : itinerarySheetDTO.getAddress()) {
                addressEntity.setId(null);
            }
            for (FuelRecordEntity fuelRecordEntity : itinerarySheetDTO.getFuelRecords()) {
                fuelRecordEntity.setId(null);
            }
            itinerarySheetDTO.setId(null);
        }
        if (itinerarySheetDTO.getCar().getRegistrationNumber() == null) {
            throw new IllegalArgumentException("Не указан автомобиль");
        }
        if (itinerarySheetDTO.getExitOdometer() == null) {
            throw new IllegalArgumentException("Не указаны показания одометра при выезде");
        }
        if (itinerarySheetDTO.getReturnOdometer() == null) {
            throw new IllegalArgumentException("Не указаны показания одометра при возвращении");
        }
        if (itinerarySheetDTO.getDriver().getLastName() == null) {
            throw new IllegalArgumentException("Не указан водитель");
        }
        if (itinerarySheetDTO.getFinalBalance() < 0) {
            throw new IllegalArgumentException("Недопустимый отрицательный остаток");
        }
        if (itinerarySheetDTO.getId() == null && itinerarySheetDTO.getAddress().getFirst().getId() != null) {
            throw new IllegalArgumentException("Адреса маршрута уже существуют");
        }
        if (itinerarySheetDTO.getId() == null && itinerarySheetDTO.getFuelRecords().getFirst().getId() != null) {
            throw new IllegalArgumentException("Заправка уже существует");
        }
        addressRepository.saveAll(itinerarySheetDTO.getAddress());
        fuelRecordsRepository.saveAll(itinerarySheetDTO.getFuelRecords());
        ItinerarySheetEntity itinerarySheet =
                itinerarySheetRepository.save(dtoTransfer.mapDtoToItinerarySheetEntity(itinerarySheetDTO));
        return dtoTransfer.mapItinerarySheetEntityToDto(itinerarySheet);
    }

    /**
     * Обновление маршрутного листа
     *
     * @param itinerarySheetDTO обновляемые данные маршрутного листа
     * @return Обновленный маршрутный лист
     * @throws IllegalArgumentException ошибка, если данные для обновления введены неверно
     */
    public ItinerarySheetDTO updateItinerarySheetDTO(ItinerarySheetDTO itinerarySheetDTO) throws IllegalArgumentException {
        return saveItinerarySheet(itinerarySheetDTO, false);
    }

    /**
     * Удаление маршрутного листа по идентификатору
     *
     * @param id идентификатор маршрутного листа
     * @return удаленный маршрутный лист
     */
    public ItinerarySheetDTO deleteItinerarySheetDTO(Long id) {
        ItinerarySheetEntity itinerarySheetEntity = itinerarySheetRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Маршрутный лист с ID = " + id + " не найден")
        );
        itinerarySheetRepository.deleteById(id);
        return dtoTransfer.mapItinerarySheetEntityToDto(itinerarySheetEntity);
    }
}
