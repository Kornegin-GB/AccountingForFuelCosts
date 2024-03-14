package ru.fsv67.services.driver;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.driver.Driver;
import ru.fsv67.models.driver.DriversLicense;
import ru.fsv67.models.driver.DriversLicenseView;
import ru.fsv67.services.GetIpAddresses;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DriverLicenseService {

    private final WebClient webClient = WebClient.builder().build();
    private final GetIpAddresses getIpAddresses;

    public void deleteDriverLicense(Long id) {
        webClient.delete()
                .uri(getIpAddresses.getDriversIp() + "/drivers/license/" + id)
                .retrieve()
                .bodyToMono(Driver.class)
                .block();
    }

    public void saveDriverLicense(DriversLicense driversLicense) {
        webClient.post()
                .uri(getIpAddresses.getDriversIp() + "/drivers/license")
                .bodyValue(driversLicense)
                .retrieve()
                .bodyToMono(DriversLicense.class)
                .block();
    }

    public void updateDriverLicense(DriversLicense driversLicense) {
        webClient.put()
                .uri(getIpAddresses.getDriversIp() + "/drivers/license")
                .bodyValue(driversLicense)
                .retrieve()
                .bodyToMono(DriversLicense.class)
                .block();
    }

    private DriversLicenseView mapToDriverLicenseView(DriversLicense driversLicense, long numberLine) {
        return new DriversLicenseView(
                driversLicense.getId(),
                numberLine,
                driversLicense.getNumberDriversLicense(),
                driversLicense.getCategories(),
                driversLicense.getDateOfIssue(),
                driversLicense.getDateOfValidity(),
                driversLicense.getIsReally(),
                driversLicense.getDriverId()
        );
    }

    public List<DriversLicenseView> mapToDriverLicenseViewList(List<DriversLicense> driversLicenseList) {
        long numberLine = 1L;
        List<DriversLicenseView> driversLicenseViewList = new ArrayList<>();
        for (DriversLicense driversLicense : driversLicenseList) {
            driversLicenseViewList.add(mapToDriverLicenseView(driversLicense, numberLine));
            numberLine++;
        }
        return driversLicenseViewList;
    }
}
