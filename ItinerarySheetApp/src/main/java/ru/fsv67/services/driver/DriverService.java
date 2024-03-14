package ru.fsv67.services.driver;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.driver.Driver;
import ru.fsv67.models.driver.DriverView;
import ru.fsv67.services.GetIpAddresses;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DriverService {
    private final WebClient webClient = WebClient.builder().build();
    private final GetIpAddresses getIpAddresses;

    /**
     * Получение списка водителей по API
     *
     * @return Список водителей
     */
    public List<Driver> getDriverList() {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getDriversIp() + "/drivers")
                    .retrieve()
                    .bodyToFlux(Driver.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void deleteDriver(Long id) {
        webClient.delete()
                .uri(getIpAddresses.getDriversIp() + "/drivers/" + id)
                .retrieve()
                .bodyToMono(Driver.class)
                .block();
    }

    public void saveDriver(Driver driver) {
        webClient.post()
                .uri(getIpAddresses.getDriversIp() + "/drivers")
                .bodyValue(driver)
                .retrieve()
                .bodyToMono(Driver.class)
                .block();
    }

    public void updateDriver(Driver driver) {
        webClient.put()
                .uri(getIpAddresses.getDriversIp() + "/drivers")
                .bodyValue(driver)
                .retrieve()
                .bodyToMono(Driver.class)
                .block();
    }

    private DriverView mapToDriverView(Driver driver, Long numberLine) {
        return new DriverView(
                driver.getId(),
                numberLine,
                driver.getLastName() + " " + driver.getFirstName() + " " + driver.getParentName(),
                driver.getSnils()
        );
    }

    public List<DriverView> mapToDriverViewList(List<Driver> driverList) {
        long numberLine = 1L;
        List<DriverView> driverViewList = new ArrayList<>();
        for (Driver driver : driverList) {
            driverViewList.add(mapToDriverView(driver, numberLine++));
        }
        return driverViewList;
    }

    public List<String> mapToDriverStringList(List<Driver> driverList) {
        List<String> stringList = new ArrayList<>();
        for (Driver driver : driverList) {
            stringList.add(driver.getLastName() + " " + driver.getFirstName() + " " + driver.getParentName());
        }
        return stringList;
    }
}
