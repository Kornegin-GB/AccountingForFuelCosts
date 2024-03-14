package ru.fsv67.services.itinerarySheet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.itinerarySheet.FuelBrand;
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

    public List<String> mapToStringFuelBrand(List<FuelBrand> fuelBrandList) {
        List<String> stringList = new ArrayList<>();
        for (FuelBrand fuelBrand : fuelBrandList) {
            stringList.add(fuelBrand.getFuelBrandName());
        }
        return stringList;
    }
}
