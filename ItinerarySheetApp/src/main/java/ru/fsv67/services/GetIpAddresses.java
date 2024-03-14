package ru.fsv67.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class GetIpAddresses {
    private final EurekaClient eurekaClient;

    /**
     * Получение IP адрес микросервиса автомобили
     *
     * @return IP адреса микросервиса автомобили
     */
    public String getCarIp() {
        Application application = eurekaClient.getApplication("CARS-MICRO-SERVICE");
        List<InstanceInfo> instanceInfoList = application.getInstances();
        int indexInstance = ThreadLocalRandom.current().nextInt(instanceInfoList.size());
        InstanceInfo randomInstanceInfo = instanceInfoList.get(indexInstance);
        return randomInstanceInfo.getHomePageUrl();
    }

    /**
     * Получение IP адрес микросервиса водители
     *
     * @return IP адреса микросервиса водители
     */
    public String getDriversIp() {
        Application application = eurekaClient.getApplication("DRIVERS-MICRO-SERVICE");
        List<InstanceInfo> instanceInfoList = application.getInstances();
        int indexInstance = ThreadLocalRandom.current().nextInt(instanceInfoList.size());
        InstanceInfo randomInstanceInfo = instanceInfoList.get(indexInstance);
        return randomInstanceInfo.getHomePageUrl();
    }

    /**
     * Получение IP адрес микросервиса маршрутных листов
     *
     * @return IP адреса микросервиса маршрутных листов
     */
    public String getItinerarySheetsIp() {
        Application application = eurekaClient.getApplication("ITINERARY-SHEETS-MICRO-SERVICE");
        List<InstanceInfo> instanceInfoList = application.getInstances();
        int indexInstance = ThreadLocalRandom.current().nextInt(instanceInfoList.size());
        InstanceInfo randomInstanceInfo = instanceInfoList.get(indexInstance);
        return randomInstanceInfo.getHomePageUrl();
    }
}
