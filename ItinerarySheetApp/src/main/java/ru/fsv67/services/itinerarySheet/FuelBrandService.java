package ru.fsv67.services.itinerarySheet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.itinerarySheet.FuelBrand;
import ru.fsv67.models.itinerarySheet.FuelBrandView;
import ru.fsv67.services.GetIpAddresses;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelBrandService {
    private final WebClient webClient = WebClient.builder().build();
    private final GetIpAddresses getIpAddresses;

    public List<FuelBrand> getFuelBrandList() {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getItinerarySheetsIp() + "itinerary/fuel")
                    .retrieve()
                    .bodyToFlux(FuelBrand.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void deleteFuelBrand(Long id) {
        try {
            webClient.delete()
                    .uri(getIpAddresses.getItinerarySheetsIp() + "itinerary/fuel/" + id)
                    .retrieve()
                    .bodyToMono(FuelBrand.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Удаление бренда не возможно");
        }

    }

    public void saveFuelBrand(FuelBrand fuelBrand) {
        webClient.post()
                .uri(getIpAddresses.getItinerarySheetsIp() + "itinerary/fuel")
                .bodyValue(fuelBrand)
                .retrieve()
                .bodyToMono(FuelBrand.class)
                .block();
    }

    public void updateFuelBrand(FuelBrand fuelBrand) {
        webClient.put()
                .uri(getIpAddresses.getItinerarySheetsIp() + "itinerary/fuel")
                .bodyValue(fuelBrand)
                .retrieve()
                .bodyToMono(FuelBrand.class)
                .block();
    }

    public List<String> mapToStringFuelBrand(List<FuelBrand> fuelBrandList) {
        List<String> stringList = new ArrayList<>();
        for (FuelBrand fuelBrand : fuelBrandList) {
            stringList.add(fuelBrand.getFuelBrandName());
        }
        return stringList;
    }

    public List<FuelBrandView> mapToFuelBrandViewList(List<FuelBrand> fuelBrandList) {
        List<FuelBrandView> fuelBrandViewList = new ArrayList<>();
        long numberLine = 1L;
        for (FuelBrand fuelBrand : fuelBrandList) {
            fuelBrandViewList.add(mapToFuelBrandView(fuelBrand, numberLine++));
        }
        return fuelBrandViewList;
    }

    private FuelBrandView mapToFuelBrandView(FuelBrand fuelBrand, Long numberLine) {
        return new FuelBrandView(
                fuelBrand.getId(),
                numberLine,
                fuelBrand.getFuelBrandName()
        );
    }
}
