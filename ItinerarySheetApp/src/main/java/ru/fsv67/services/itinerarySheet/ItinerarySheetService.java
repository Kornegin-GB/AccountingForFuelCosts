package ru.fsv67.services.itinerarySheet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.itinerarySheet.ItinerarySheet;
import ru.fsv67.models.itinerarySheet.ItinerarySheetView;
import ru.fsv67.services.GetIpAddresses;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItinerarySheetService {
    private final WebClient webClient = WebClient.builder().build();
    private final GetIpAddresses getIpAddresses;
    private final AddressService addressService;
    private final FuelRecordService fuelRecordService;

    /**
     * Получение всех маршрутных листов
     *
     * @return Все маршрутные листы
     */
    public List<ItinerarySheet> getItinerarySheetList() {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getItinerarySheetsIp() + "/itinerary")
                    .retrieve()
                    .bodyToFlux(ItinerarySheet.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Получить список маршрутных листов по идентификатору автомобиля
     *
     * @param id идентификатор автомобиля
     * @return Список маршрутных листов по идентификатору автомобиля
     */
    public List<ItinerarySheet> getItinerarySheetListByCarId(Long id) {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getItinerarySheetsIp() + "/itinerary/" + id)
                    .retrieve()
                    .bodyToFlux(ItinerarySheet.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Получить последний маршрутный лист по идентификатору автомобиля
     *
     * @param id идентификатор автомобиля
     * @return Последний маршрутный лист по идентификатору автомобиля
     */
    public ItinerarySheet getLastItinerarySheet(Long id) {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getItinerarySheetsIp() + "/itinerary/car/" + id)
                    .retrieve()
                    .bodyToMono(ItinerarySheet.class)
                    .block();
        } catch (Exception e) {
            return new ItinerarySheet();
        }
    }

    public void saveItinerarySheet(ItinerarySheet itinerarySheet) {
        webClient.post()
                .uri(getIpAddresses.getItinerarySheetsIp() + "/itinerary")
                .bodyValue(itinerarySheet)
                .retrieve()
                .bodyToMono(ItinerarySheet.class)
                .block();
    }

    public void updateItinerarySheet(ItinerarySheet itinerarySheet) {
        webClient.put()
                .uri(getIpAddresses.getItinerarySheetsIp() + "/itinerary")
                .bodyValue(itinerarySheet)
                .retrieve()
                .bodyToMono(ItinerarySheet.class)
                .block();
    }

    public void deleteItinerarySheet(Long id) {
        webClient.delete()
                .uri(getIpAddresses.getItinerarySheetsIp() + "/itinerary/" + id)
                .retrieve()
                .bodyToMono(ItinerarySheet.class)
                .block();
    }

    public List<ItinerarySheetView> mapToItinerarySheetViewList(List<ItinerarySheet> itinerarySheetList) {
        long numberLine = 1L;
        List<ItinerarySheetView> itinerarySheetViewList = new ArrayList<>();
        for (ItinerarySheet itinerarySheet : itinerarySheetList) {
            itinerarySheetViewList.add(mapToItinerarySheetView(itinerarySheet, numberLine++));
        }
        return itinerarySheetViewList;
    }

    private ItinerarySheetView mapToItinerarySheetView(ItinerarySheet itinerarySheet, Long numberLine) {
        return new ItinerarySheetView(
                itinerarySheet.getId(),
                numberLine,
                itinerarySheet.getDocumentSeries() + "-" + itinerarySheet.getDocumentNumber(),
                itinerarySheet.getDepartureDate(),
                itinerarySheet.getReturnDate(),
                itinerarySheet.getCar().getCarModel().getCarBrand().getBrandName() + " "
                        + itinerarySheet.getCar().getCarModel().getModelName() + " "
                        + itinerarySheet.getCar().getRegistrationNumber(),
                itinerarySheet.getExitOdometer(),
                itinerarySheet.getReturnOdometer(),
                itinerarySheet.getDriver().getLastName() + " "
                        + itinerarySheet.getDriver().getFirstName() + " "
                        + itinerarySheet.getDriver().getParentName(),
                itinerarySheet.getInitialBalance(),
                itinerarySheet.getFuelConsumption(),
                itinerarySheet.getFinalBalance(),
                fuelRecordService.mapFuelRecordListToFuelRecordViewList(itinerarySheet.getFuelRecords()),
                addressService.mapToAddressViewList(itinerarySheet.getAddress())
        );
    }
}
