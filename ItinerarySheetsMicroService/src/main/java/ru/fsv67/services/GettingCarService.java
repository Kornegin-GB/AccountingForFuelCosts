package ru.fsv67.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.fsv67.dto.car.Car;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GettingCarService {
    private final WebClient webClient = WebClient.builder().build();
    private final EurekaClient eurekaClient;

    /**
     * Получение автомобиля по идентификатору
     *
     * @param id идентификатор автомобиля
     * @return данные об автомобиле
     */
    public Car getCarById(Long id) {
        Car car;
        try {
            car = getCarData(id);
        } catch (WebClientResponseException e) {
            throw new NoSuchElementException("Автомобиль с ID = " + id + " не найден");
        } catch (Exception e) {
            throw new RuntimeException("Соединение с сервером автомобили не установлено");
        }
        return car;
    }


    private Car getCarData(Long id) {
        try {
            return webClient.get()
                    .uri(getCarIp() + "/cars/" + id)
                    .retrieve()
                    .bodyToMono(Car.class)
                    .block();
        } catch (Exception e) {
            System.out.println("Соединение с сервером автомобили не установлено");
            return new Car();
        }

    }

    private String getCarIp() {
        Application application = eurekaClient.getApplication("CARS-MICRO-SERVICE");
        List<InstanceInfo> instanceInfoList = application.getInstances();
        int indexInstance = ThreadLocalRandom.current().nextInt(instanceInfoList.size());
        InstanceInfo randomInstanceInfo = instanceInfoList.get(indexInstance);
        return randomInstanceInfo.getHomePageUrl();
    }
}
