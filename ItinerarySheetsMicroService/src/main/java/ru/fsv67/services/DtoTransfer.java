package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fsv67.dto.ItinerarySheetDTO;
import ru.fsv67.models.ItinerarySheetEntity;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DtoTransfer {
    private final GettingCarService car;
    private final GettingDriverService driver;

    /**
     * Преобразование вывода для пользователя
     *
     * @param itinerarySheet данные из БД
     * @return Данные для пользователя
     */
    public ItinerarySheetDTO mapItinerarySheetEntityToDto(ItinerarySheetEntity itinerarySheet) {
        ItinerarySheetDTO itinerarySheetDTO = new ItinerarySheetDTO();
        itinerarySheetDTO.setId(itinerarySheet.getId());
        itinerarySheetDTO.setDocumentNumber(itinerarySheet.getDocumentNumber());
        itinerarySheetDTO.setDocumentSeries(itinerarySheet.getDocumentSeries());
        itinerarySheetDTO.setDepartureDate(itinerarySheet.getDepartureDate());
        itinerarySheetDTO.setReturnDate(itinerarySheet.getReturnDate());
        itinerarySheetDTO.setCar(car.getCarById(itinerarySheet.getCarId()));
        itinerarySheetDTO.setExitOdometer(itinerarySheet.getExitOdometer());
        itinerarySheetDTO.setReturnOdometer(itinerarySheet.getReturnOdometer());
        itinerarySheetDTO.setDriver(driver.getDriverById(itinerarySheet.getDriverId()));
        itinerarySheetDTO.setInitialBalance(itinerarySheet.getInitialBalance());
        itinerarySheetDTO.setFuelConsumption(itinerarySheet.getFuelConsumption());
        itinerarySheetDTO.setFinalBalance(itinerarySheet.getFinalBalance());
        itinerarySheetDTO.setFuelRecords(itinerarySheet.getFuelRecords());
        itinerarySheet.getAddress().sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        itinerarySheetDTO.setAddress(itinerarySheet.getAddress());
        return itinerarySheetDTO;
    }

    /**
     * Преобразование списка для пользователя
     *
     * @param itinerarySheetEntityList список полученный из БД
     * @return Список для пользователя
     */
    public List<ItinerarySheetDTO> mapItinerarySheetEntityToDtoList(List<ItinerarySheetEntity> itinerarySheetEntityList) {
        List<ItinerarySheetDTO> itinerarySheetDTOList = new ArrayList<>();
        for (ItinerarySheetEntity itinerarySheet : itinerarySheetEntityList) {
            itinerarySheetDTOList.add(mapItinerarySheetEntityToDto(itinerarySheet));
        }
        return itinerarySheetDTOList;
    }

    /**
     * Преобразование вывода для пользователя
     *
     * @param itinerarySheetDTO данные для пользователя
     * @return Данные для записи в БД
     */
    public ItinerarySheetEntity mapDtoToItinerarySheetEntity(ItinerarySheetDTO itinerarySheetDTO) {
        ItinerarySheetEntity itinerarySheetEntity = new ItinerarySheetEntity();
        itinerarySheetEntity.setId(itinerarySheetDTO.getId());
        itinerarySheetEntity.setDocumentNumber(itinerarySheetDTO.getDocumentNumber());
        itinerarySheetEntity.setDocumentSeries(itinerarySheetDTO.getDocumentSeries());
        itinerarySheetEntity.setDepartureDate(itinerarySheetDTO.getDepartureDate());
        itinerarySheetEntity.setReturnDate(itinerarySheetDTO.getReturnDate());
        itinerarySheetEntity.setCarId(itinerarySheetDTO.getCar().getId());
        itinerarySheetEntity.setExitOdometer(itinerarySheetDTO.getExitOdometer());
        itinerarySheetEntity.setReturnOdometer(itinerarySheetDTO.getReturnOdometer());
        itinerarySheetEntity.setDriverId(itinerarySheetDTO.getDriver().getId());
        itinerarySheetEntity.setInitialBalance(itinerarySheetDTO.getInitialBalance());
        itinerarySheetEntity.setFuelConsumption(itinerarySheetDTO.getFuelConsumption());
        itinerarySheetEntity.setFinalBalance(itinerarySheetDTO.getFinalBalance());
        itinerarySheetEntity.setFuelRecords(itinerarySheetDTO.getFuelRecords());
        itinerarySheetEntity.setAddress(itinerarySheetDTO.getAddress());
        return itinerarySheetEntity;
    }
}
