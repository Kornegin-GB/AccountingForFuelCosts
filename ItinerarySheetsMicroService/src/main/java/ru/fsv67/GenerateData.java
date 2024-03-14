package ru.fsv67;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fsv67.dto.car.Car;
import ru.fsv67.models.AddressEntity;
import ru.fsv67.models.ItinerarySheetEntity;
import ru.fsv67.models.fuel.FuelBrandEntity;
import ru.fsv67.models.fuel.FuelRecordEntity;
import ru.fsv67.repositories.AddressRepository;
import ru.fsv67.repositories.FuelBrandRepository;
import ru.fsv67.repositories.FuelRecordsRepository;
import ru.fsv67.repositories.ItinerarySheetRepository;
import ru.fsv67.services.GettingCarService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateData {
    private final FuelBrandRepository fuelBrandRepository;
    private final ItinerarySheetRepository itinerarySheetRepository;
    private final GettingCarService gettingCarService;
    private final AddressRepository addressRepository;
    private final FuelRecordsRepository fuelRecordsRepository;

    @PostConstruct
    void generateData() {
        FuelBrandEntity ai_92 = fuelBrandRepository.save(new FuelBrandEntity("АИ-92"));
        FuelBrandEntity ai_95 = fuelBrandRepository.save(new FuelBrandEntity("АИ-95"));
        FuelBrandEntity dt = fuelBrandRepository.save(new FuelBrandEntity("ДТ"));
        FuelBrandEntity gaz = fuelBrandRepository.save(new FuelBrandEntity("ГАЗ"));

        itinerarySheetRepository.save(fillingItinerarySheet(
                LocalDateTime.now().minusWeeks(1),
                LocalDateTime.now(),
                1L,
                2L,
                12622,
                12722,
                List.of(
                        new AddressEntity("Смоленск"),
                        new AddressEntity("Вязьма"),
                        new AddressEntity("Смоленск")
                ),
                List.of(
                        new FuelRecordEntity(
                                LocalDate.now().minusWeeks(1),
                                ai_92,
                                50.0,
                                56.0,
                                50.0 * 56.0
                        )
                )
        ));
        itinerarySheetRepository.save(fillingItinerarySheet(
                LocalDateTime.now().minusWeeks(3),
                LocalDateTime.now().minusWeeks(2),
                2L,
                1L,
                11235,
                11484,
                List.of(
                        new AddressEntity("Смоленск"),
                        new AddressEntity("Рославль"),
                        new AddressEntity("Смоленск")
                ),
                List.of(
                        new FuelRecordEntity(
                                LocalDate.now().minusWeeks(1),
                                ai_95,
                                50.0,
                                54.0,
                                50.0 * 54.0
                        ),
                        new FuelRecordEntity(
                                LocalDate.now().minusWeeks(1),
                                ai_95,
                                50.0,
                                56.0,
                                50.0 * 56.0
                        )
                )
        ));
        itinerarySheetRepository.save(fillingItinerarySheet(
                LocalDateTime.now().minusWeeks(2),
                LocalDateTime.now().minusWeeks(1),
                2L,
                3L,
                11484,
                12284,
                List.of(
                        new AddressEntity("Смоленск"),
                        new AddressEntity("Москва"),
                        new AddressEntity("Смоленск")
                ),
                List.of(
                        new FuelRecordEntity(
                                LocalDate.now().minusWeeks(1),
                                ai_95,
                                30.0,
                                58.0,
                                30.0 * 58.0
                        )
                )
        ));
    }

    private ItinerarySheetEntity fillingItinerarySheet(LocalDateTime departureDate, LocalDateTime returnDate,
                                                       Long carId, Long driverId,
                                                       int exitOdometer,
                                                       int returnOdometer,
                                                       List<AddressEntity> addressEntityList,
                                                       List<FuelRecordEntity> fuelRecordList) {
        ItinerarySheetEntity itinerarySheetEntity = itinerarySheetRepository.findFirstByCarIdOrderByIdDesc(carId);
        Car car = gettingCarService.getCarById(carId);

        Double initialBalance = getCurrentFuelBalance(itinerarySheetEntity);
        Double fuelConsumption = calculateFuelConsumption(
                car.getFuelConsumptionRate(),
                exitOdometer,
                returnOdometer
        );
        Double finalBalance = calculateFinalBalance(
                initialBalance,
                fuelConsumption,
                calculateFuelQuantity(fuelRecordList));

        ItinerarySheetEntity itinerarySheet = new ItinerarySheetEntity();
        itinerarySheet.setDocumentNumber(itinerarySheetRepository.countByCarId(carId) + 1);
        itinerarySheet.setDocumentSeries(car.getSeriesItinerarySheet());
        itinerarySheet.setDepartureDate(departureDate);
        itinerarySheet.setReturnDate(returnDate);
        itinerarySheet.setCarId(carId);
        itinerarySheet.setExitOdometer(exitOdometer);
        itinerarySheet.setReturnOdometer(returnOdometer);
        itinerarySheet.setDriverId(driverId);
        itinerarySheet.setInitialBalance(initialBalance);
        itinerarySheet.setFuelConsumption(fuelConsumption);
        itinerarySheet.setFinalBalance(finalBalance);
        itinerarySheet.setAddress(addressRepository.saveAll(addressEntityList));
        itinerarySheet.setFuelRecords(fuelRecordsRepository.saveAll(fuelRecordList));
        return itinerarySheet;
    }

    private Double getCurrentFuelBalance(ItinerarySheetEntity itinerarySheet) {
        if (itinerarySheet == null) {
            return 0.0;
        } else {
            return itinerarySheet.getFinalBalance();
        }
    }

    public Double calculateFuelConsumption(Double fuelRate, int exitOdometer, int returnOdometer) {
        return (returnOdometer - exitOdometer) * fuelRate / 100;
    }

    public Double calculateFinalBalance(Double initialBalance, Double fuelConsumption, Double refueled) {
        return initialBalance + refueled - fuelConsumption;
    }

    public Double calculateFuelQuantity(List<FuelRecordEntity> fuelRecordList) {
        Double quantity = 0.0;
        if (fuelRecordList.isEmpty()) {
            return quantity;
        }
        for (FuelRecordEntity fuelRecord : fuelRecordList) {
            quantity += fuelRecord.getFuelQuantity();
        }
        return quantity;
    }

}
