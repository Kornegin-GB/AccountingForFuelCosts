package ru.fsv67.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.dto.ModelDTO;
import ru.fsv67.models.CarModel;
import ru.fsv67.repositories.CarModelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Логика работы с моделями автомобиля
 */
@Service
@RequiredArgsConstructor
public class ModelService {
    private final CarModelRepository carModelRepository;
    private final BrandService brandService;

    /**
     * Метод поиска всех моделей автомобиля в системе
     *
     * @return список моделей автомобилей в системе
     */
    public List<ModelDTO> findModelAll() {
        List<ModelDTO> carModelList = mapToDTOList(carModelRepository.findAll());
        if (carModelList.isEmpty()) {
            throw new NoSuchElementException("Список моделей автомобилей пуст");
        }
        carModelList.sort((o1, o2) -> Math.toIntExact(o1.getId() - o2.getId()));
        return carModelList;
    }

    /**
     * Метод поиска модели автомобиля по идентификатору
     *
     * @param id идентификатор модели автомобиля
     * @return Модель автомобиля с искомым идентификатором
     */
    public ModelDTO findModelById(Long id) {
        CarModel model = carModelRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Модель автомобиля с ID = " + id + " не найдена")
        );
        return mapToDTO(model);
    }

    /**
     * Метод сохранения модели автомобиля в БД
     *
     * @param modelDTO сохраняемые данные модели автомобиля
     * @return сохраненная модель автомобиля
     */
    public ModelDTO saveModel(ModelDTO modelDTO) {
        CarModel carModel = mapDTOToModel(modelDTO);
        CarModel model = carModelRepository.findCarModelByModelName(carModel.getModelName());
        if (model != null) {
            throw new IllegalArgumentException(
                    "Модель автомобиля с названием " + model.getModelName() + " существует"
            );
        }
        if (carModel.getModelName().isEmpty()) {
            throw new IllegalArgumentException("Название модели автомобиля не указано");
        }
        CarModel newCarModel = carModelRepository.save(carModel);
        return mapToDTO(newCarModel);
    }

    /**
     * Метод удаления модели автомобиля из системы
     *
     * @param id идентификатор удаляемой модели автомобиля
     * @return Удаленная модели автомобиля
     * @throws NoSuchElementException ошибка, если идентификатор модели автомобиля не найден
     */
    public ModelDTO deleteModelById(Long id) throws NoSuchElementException {
        ModelDTO carModel = findModelById(id);
        carModelRepository.deleteById(id);
        return carModel;
    }

    /**
     * Метод изменения модели автомобиля
     *
     * @param model изменяемые данные модели автомобиля
     * @return Измененная модель автомобиля
     * @throws NoSuchElementException   ошибка, если изменяемая модель автомобиля не существует в БД
     * @throws IllegalArgumentException ошибка, если изменяемые данные пустые
     */
    public ModelDTO updateModelById(ModelDTO model) throws NoSuchElementException, IllegalArgumentException {
        if (model.getId() == null) {
            throw new IllegalArgumentException("Не указан id обновляемой марки топлива");
        }
        findModelById(model.getId());
        return saveModel(model);
    }

    private ModelDTO mapToDTO(CarModel carModel) {
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setId(carModel.getId());
        modelDTO.setModelName(carModel.getModelName());
        modelDTO.setCarBrand(brandService.findBrandById(carModel.getCarBrand()));
        return modelDTO;
    }

    private CarModel mapDTOToModel(ModelDTO modelDTO) {
        CarModel carModel = new CarModel();
        carModel.setId(modelDTO.getId());
        carModel.setModelName(modelDTO.getModelName());
        carModel.setCarBrand(modelDTO.getCarBrand().getId());
        return carModel;
    }

    private List<ModelDTO> mapToDTOList(List<CarModel> carModelList) {
        List<ModelDTO> modelDTOList = new ArrayList<>();
        for (CarModel model : carModelList) {
            modelDTOList.add(mapToDTO(model));
        }
        return modelDTOList;
    }
}