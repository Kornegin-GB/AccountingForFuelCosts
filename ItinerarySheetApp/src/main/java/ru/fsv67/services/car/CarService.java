package ru.fsv67.services.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.car.Car;
import ru.fsv67.models.car.CarView;
import ru.fsv67.services.GetIpAddresses;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private final WebClient webClient = WebClient.builder().build();
    private final GetIpAddresses getIpAddresses;

    /**
     * Соединение с сервером автомобили по API и получение списка записей
     *
     * @return Список автомобилей
     */
    public List<Car> getCarList() {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getCarIp() + "/cars")
                    .retrieve()
                    .bodyToFlux(Car.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Запись автомобиля на сервере
     *
     * @param newCar записываемые данные автомобиля
     */
    public void saveCarData(Car newCar) {
        webClient.post()
                .uri(getIpAddresses.getCarIp() + "/cars")
                .bodyValue(newCar)
                .retrieve()
                .bodyToMono(Car.class)
                .block();
    }

    /**
     * Удаление автомобиля из БД по API сервера
     *
     * @param id идентификатор автомобиля в базе данных
     */
    public void deleteCarData(Long id) {
        webClient.delete()
                .uri(getIpAddresses.getCarIp() + "/cars/" + id)
                .retrieve()
                .bodyToMono(Car.class)
                .block();
    }

    /**
     * Обновление данных автомобиля
     *
     * @param car обновленные данные автомобиля
     */
    public void updateCarData(Car car) {
        webClient.put()
                .uri(getIpAddresses.getCarIp() + "/cars")
                .bodyValue(car)
                .retrieve()
                .bodyToMono(Car.class)
                .block();
    }

    /**
     * Преобразование списка автомобилей в отображаемый список автомобилей на форме
     *
     * @param carList список автомобилей
     * @return Преобразованный список автомобилей
     */
    public List<CarView> mapToCarViewList(List<Car> carList) {
        long numberField = 1L;
        List<CarView> carViewList = new ArrayList<>();
        for (Car car : carList) {
            carViewList.add(mapToCarViewData(car, numberField++));
        }
        return carViewList;
    }

    /**
     * Преобразование данных автомобиля в отображаемые данные на форме
     *
     * @param car         данные автомобиля
     * @param numberField порядковый номер строки
     * @return Преобразованные данные автомобиля для отображения
     */
    private CarView mapToCarViewData(Car car, Long numberField) {
        return new CarView(
                car.getId(),
                numberField,
                car.getRegistrationNumber(),
                car.getVin(),
                car.getFuelConsumptionRate(),
                car.getSeriesItinerarySheet(),
                car.getInitialOdometer(),
                car.getCarModel().getModelName(),
                car.getCarModel().getCarBrand().getBrandName()
        );
    }

    public List<String> mapToCarStringList(List<Car> carList) {
        List<String> stringList = new ArrayList<>();
        for (Car car : carList) {
            stringList.add(
                    car.getCarModel().getCarBrand().getBrandName() + " "
                            + car.getCarModel().getModelName() + " "
                            + car.getRegistrationNumber()
            );
        }
        return stringList;
    }
}
