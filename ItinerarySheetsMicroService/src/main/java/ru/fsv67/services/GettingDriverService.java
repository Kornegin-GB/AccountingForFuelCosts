package ru.fsv67.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.fsv67.dto.driver.Driver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GettingDriverService {
    private final WebClient webClient = WebClient.builder().build();
    private final EurekaClient eurekaClient;

    public Driver getDriverById(Long id) {
        Driver driver;
        try {
            driver = webClient.get()
                    .uri(getDriverIp() + "/drivers/" + id)
                    .retrieve()
                    .bodyToMono(Driver.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new NoSuchElementException("Водитель с ID = " + id + " не найден");
        } catch (Exception e) {
            throw new RuntimeException("Соединение с сервером водители не установлено");
        }
        return driver;
    }

    private String getDriverIp() {
        Application application = eurekaClient.getApplication("DRIVERS-MICRO-SERVICE");
        List<InstanceInfo> instanceInfoList = application.getInstances();
        int indexInstance = ThreadLocalRandom.current().nextInt(instanceInfoList.size());
        InstanceInfo randomInstanceInfo = instanceInfoList.get(indexInstance);
        return randomInstanceInfo.getHomePageUrl();
    }
}
