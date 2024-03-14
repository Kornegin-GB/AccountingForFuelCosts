package ru.fsv67.services.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.models.car.Model;
import ru.fsv67.models.car.ModelView;
import ru.fsv67.services.GetIpAddresses;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ModelService {
    private final WebClient webClient = WebClient.builder().build();
    private final GetIpAddresses getIpAddresses;


    /**
     * Получение списка моделей автомобилей по API на сервере
     *
     * @return Список моделей автомобилей
     */
    public List<Model> getModelList() {
        try {
            return webClient.get()
                    .uri(getIpAddresses.getCarIp() + "/cars/model")
                    .retrieve()
                    .bodyToFlux(Model.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    /**
     * Удаление модели по API на сервере
     *
     * @param id идентификатор модели в базе данных
     */
    public void deleteModelData(Long id) {
        webClient.delete()
                .uri(getIpAddresses.getCarIp() + "/cars/model/" + id)
                .retrieve()
                .bodyToMono(Model.class)
                .block();
    }

    /**
     * Сохранение модели по API на сервере
     *
     * @param model сохраняемые данные модели
     */
    public void saveModelData(Model model) {
        webClient.post()
                .uri(getIpAddresses.getCarIp() + "/cars/model")
                .bodyValue(model)
                .retrieve()
                .bodyToMono(Model.class)
                .block();
    }

    /**
     * Обновление модели по API на сервере
     *
     * @param model обновляемые данные модели
     */
    public void updateModelData(Model model) {
        webClient.put()
                .uri(getIpAddresses.getCarIp() + "/cars/model")
                .bodyValue(model)
                .retrieve()
                .bodyToMono(Model.class)
                .block();
    }

    /**
     * Преобразование список моделей автомобилей в строковый список названий
     *
     * @param modelList список моделей автомобилей
     * @return Строковый список названий моделей автомобилей
     */
    public List<String> mapModelListToStringList(List<Model> modelList) {
        List<String> brandViews = new ArrayList<>();
        for (Model model : modelList) {
            brandViews.add(model.getModelName());
        }
        return brandViews;
    }

    /**
     * Преобразование списка моделей в список моделей для отображения на форме
     *
     * @param modelList список моделей автомобилей
     * @return Список моделей для отображения на форме
     */
    public List<ModelView> mapToModelViewList(List<Model> modelList) {
        List<ModelView> modelViews = new ArrayList<>();
        long numberLine = 1L;
        for (Model model : modelList) {
            modelViews.add(mapToModelView(model, numberLine++));
        }
        return modelViews;
    }

    /**
     * Преобразование модели в модель для отображения на форме
     *
     * @param model      модель автомобиля
     * @param numberLine номер строки
     * @return Модель автомобиля для отображения
     */
    private ModelView mapToModelView(Model model, Long numberLine) {
        return new ModelView(
                model.getId(),
                numberLine,
                model.getModelName(),
                model.getCarBrand().getBrandName()
        );
    }
}
