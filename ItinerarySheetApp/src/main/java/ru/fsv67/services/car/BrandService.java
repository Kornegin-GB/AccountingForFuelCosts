package ru.fsv67.services.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.car.Brand;
import ru.fsv67.models.car.BrandView;
import ru.fsv67.services.GetIpAddresses;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BrandService {
    private final WebClient webClient = WebClient.builder().build();
    private final GetIpAddresses getIpAddresses;

    /**
     * Получение списка брендов автомобилей по API на сервере
     *
     * @return Список брендов
     */
    public List<Brand> getBrandList() {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getCarIp() + "/cars/brand")
                    .retrieve()
                    .bodyToFlux(Brand.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Удаление бренда по API на сервере
     *
     * @param id идентификатор бренда в базе данных
     */
    public void deleteBrandData(Long id) {
        webClient.delete()
                .uri(getIpAddresses.getCarIp() + "/cars/brand/" + id)
                .retrieve()
                .bodyToMono(Brand.class)
                .block();
    }

    /**
     * Сохранение бренда по API на сервере
     *
     * @param brand сохраняемые данные бренда
     */
    public void saveBrandData(Brand brand) {
        webClient.post()
                .uri(getIpAddresses.getCarIp() + "/cars/brand")
                .bodyValue(brand)
                .retrieve()
                .bodyToMono(Brand.class)
                .block();
    }

    /**
     * Обновление бренда по API на сервере
     *
     * @param brand обновляемые данные бренда
     */
    public void updateBrandData(Brand brand) {
        webClient.put()
                .uri(getIpAddresses.getCarIp() + "/cars/brand")
                .bodyValue(brand)
                .retrieve()
                .bodyToMono(Brand.class)
                .block();
    }

    /**
     * Преобразование список брендов автомобилей в строковый список названий
     *
     * @param brandList список брендов автомобилей
     * @return Строковый список названий брендов автомобилей
     */
    public List<String> mapBrandListToStringList(List<Brand> brandList) {
        List<String> brandViews = new ArrayList<>();
        for (Brand brand : brandList) {
            brandViews.add(brand.getBrandName());
        }
        return brandViews;
    }

    /**
     * Преобразование списка брендов в список брендов для отображения на форме
     *
     * @param brandList список брендов автомобилей
     * @return Список брендов для отображения на форме
     */
    public List<BrandView> mapToBrandViewList(List<Brand> brandList) {
        List<BrandView> brandViews = new ArrayList<>();
        long numberLine = 1L;
        for (Brand brand : brandList) {
            brandViews.add(mapToBrandView(brand, numberLine++));
        }
        return brandViews;
    }

    /**
     * Преобразование бренда в бренд для отображения на форме
     *
     * @param brand      бренд автомобиля
     * @param numberLine номер строки
     * @return Бренд автомобиля для отображения
     */
    private BrandView mapToBrandView(Brand brand, Long numberLine) {
        return new BrandView(
                brand.getId(),
                numberLine,
                brand.getBrandName()
        );
    }
}
