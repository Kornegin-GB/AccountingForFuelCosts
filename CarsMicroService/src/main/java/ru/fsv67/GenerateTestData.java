package ru.fsv67;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fsv67.models.CarBrand;
import ru.fsv67.models.CarEntity;
import ru.fsv67.models.CarModel;
import ru.fsv67.repositories.CarBrandRepository;
import ru.fsv67.repositories.CarModelRepository;
import ru.fsv67.repositories.CarsRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateTestData {
    private final CarsRepository carsRepository;
    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;

    @PostConstruct
    void generateCars() {
        CarBrand audi = carBrandRepository.save(new CarBrand("Audi"));
        CarBrand vaz = carBrandRepository.save(new CarBrand("ВАЗ"));
        CarBrand lada = carBrandRepository.save(new CarBrand("Lada"));
        CarBrand wv = carBrandRepository.save(new CarBrand("WV"));

        CarModel a6 = carModelRepository.save(new CarModel("А6", audi.getId()));
        CarModel seven = carModelRepository.save(new CarModel("2107", vaz.getId()));
        CarModel largus = carModelRepository.save(new CarModel("Largus", lada.getId()));
        CarModel polo = carModelRepository.save(new CarModel("Polo", wv.getId()));
        CarModel passat = carModelRepository.save(new CarModel("Passat", wv.getId()));
        carsRepository.saveAll(
                List.of(
                        new CarEntity("А111АА67", "XTW55555000000000", a6.getId(), 12.0, 1, 12563),
                        new CarEntity("Б222ББ67", "XTW66666000000000", seven.getId(), 8.0, 2, 11025),
                        new CarEntity("Х333ХХ67", "XTW77777000000000", largus.getId(), 11.0, 3, 125630),
                        new CarEntity("К444КК67", "XTW99999000000000", polo.getId(), 8.6, 4, 120950)
                )
        );
    }
}
