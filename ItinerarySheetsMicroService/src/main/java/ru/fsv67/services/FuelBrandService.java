package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.models.fuel.FuelBrandEntity;
import ru.fsv67.repositories.FuelBrandRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FuelBrandService {
    private final FuelBrandRepository fuelBrandRepository;

    public List<FuelBrandEntity> findFuelBrandAll() {
        List<FuelBrandEntity> fuelBrandEntityList = fuelBrandRepository.findAll();
        if (fuelBrandEntityList.isEmpty()) {
            throw new NoSuchElementException("Список брендов топлива пуст");
        }
        return fuelBrandEntityList;
    }

    public FuelBrandEntity findFuelBrandById(Long id) {
        return fuelBrandRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Бренд с Id = " + id + " не найден")
        );
    }

    public FuelBrandEntity saveFuelBrand(FuelBrandEntity fuelBrandEntity) {
        if (fuelBrandEntity.getFuelBrandName().isEmpty()) {
            throw new IllegalArgumentException("Название бренда не задано");
        }
        return fuelBrandRepository.save(fuelBrandEntity);
    }
}
