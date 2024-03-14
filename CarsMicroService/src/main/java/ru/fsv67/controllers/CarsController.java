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
import ru.fsv67.dto.CarDTO;
import ru.fsv67.services.CarsService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Контроллер взаимодействия клиента с автомобилями
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
@Tag(name = "Автомобили", description = "Операции с автомобилями")
public class CarsController {
    private final CarsService carsService;

    @Operation(
            summary = "Получение списка автомобилей",
            description = "Получение всего списка автомобилей",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка автомобилей",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = CarDTO.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список автомобилей пуст.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping
    public ResponseEntity<List<CarDTO>> getCarList() {
        List<CarDTO> carEntityList;
        try {
            carEntityList = carsService.findCarAll();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carEntityList);
    }

    @Operation(
            summary = "Получение автомобиля",
            description = "Получение автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение автомобиля", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CarDTO.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404",
                            description = "Автомобиль с искомым идентификатором не найден",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        CarDTO carEntity;
        try {
            carEntity = carsService.findCarById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carEntity);
    }

    @Operation(
            summary = "Запись автомобиля",
            description = "Записать нового автомобиля в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное сохранение автомобиля в БД",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CarDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "405", description = "Не все идентификационные данные введены",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PostMapping
    public ResponseEntity<CarDTO> addNewCar(@RequestBody CarDTO carEntity) {
        CarDTO car;
        try {
            car = carsService.saveCar(carEntity, true);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @Operation(
            summary = "Удаление автомобиля",
            description = "Удаление автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Удаление автомобиля прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CarDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Автомобиль для удаления не найден",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<CarDTO> deleteCarById(@PathVariable Long id) {
        CarDTO carEntity;
        try {
            carEntity = carsService.deleteCarById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carEntity);
    }

    @Operation(
            summary = "Изменение данных автомобиля",
            description = "Изменение данных автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Изменение данных автомобиля прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CarDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Автомобиль для изменения данных не найден",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            }),
                    @ApiResponse(responseCode = "405", description = "Не все идентификационные данные введены",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PutMapping
    public ResponseEntity<CarDTO> updateCarById(@RequestBody CarDTO carEntity) {
        CarDTO car;
        try {
            car = carsService.updateCarById(carEntity);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }
}
