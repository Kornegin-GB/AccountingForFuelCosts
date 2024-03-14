package ru.fsv67.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fsv67.dto.ModelDTO;
import ru.fsv67.services.ModelService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Контроллер взаимодействия клиента с моделями автомобиля
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars/model")
@Tag(name = "Модель автомобилей", description = "Операции с моделями автомобилей")
public class ModelController {
    private final ModelService modelService;

    @Operation(
            summary = "Получение списка моделей автомобиля",
            description = "Получение всего списка моделей автомобилей",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка моделей автомобилей",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = ModelDTO.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список моделей автомобилей пуст.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping
    public ResponseEntity<List<ModelDTO>> findModelAll() {
        List<ModelDTO> carModelList;
        try {
            carModelList = modelService.findModelAll();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carModelList);
    }
    
    @Operation(
            summary = "Получение модели автомобиля",
            description = "Получение модели автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение модели автомобиля",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ModelDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Модель автомобиля с искомым идентификатором не найден",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ModelDTO> findModelById(@PathVariable Long id) {
        ModelDTO carModel;
        try {
            carModel = modelService.findModelById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carModel);
    }

    @Operation(
            summary = "Запись модели автомобиля",
            description = "Записать новую модель автомобиля в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное сохранение модели автомобиля в БД",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ModelDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "405", description = "Не введено название модели автомобиля",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PostMapping
    public ResponseEntity<ModelDTO> addNewModel(@RequestBody ModelDTO carModel) {
        ModelDTO model;
        try {
            model = modelService.saveModel(carModel);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @Operation(
            summary = "Удаление модели автомобиля",
            description = "Удаление модели автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Удаление модели автомобиля прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ModelDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Модель автомобиля для удаления не найден",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ModelDTO> deleteModelById(@PathVariable Long id) {
        ModelDTO carModel;
        try {
            carModel = modelService.deleteModelById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carModel);
    }

    @Operation(
            summary = "Изменение модели автомобиля",
            description = "Изменение модели автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Изменение модели автомобиля прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ModelDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Модель для изменения не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "405", description = "Не введено название модели автомобиля",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PutMapping
    public ResponseEntity<ModelDTO> updateModelById(@RequestBody ModelDTO carModel) {
        ModelDTO model;
        try {
            model = modelService.updateModelById(carModel);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
}
