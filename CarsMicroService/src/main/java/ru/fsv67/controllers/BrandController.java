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
import ru.fsv67.models.CarBrand;
import ru.fsv67.services.BrandService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Контроллер взаимодействия клиента с брендами автомобиля
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cars/brand")
@Tag(name = "Бренд автомобилей", description = "Операции с брендами автомобилей")
public class BrandController {
    private final BrandService brandService;

    @Operation(
            summary = "Получение списка брендов автомобилей",
            description = "Получение всего списка брендов автомобилей",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка брендов автомобилей",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = CarBrand.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список брендов автомобилей пуст.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping
    public ResponseEntity<List<CarBrand>> findBrandAll() {
        final List<CarBrand> carBrandList;
        try {
            carBrandList = brandService.findBrandAll();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carBrandList);
    }

    @Operation(
            summary = "Получение бренда автомобиля",
            description = "Получение бренда автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение бренда автомобиля",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CarBrand.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Бренд автомобиля с искомым идентификатором не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CarBrand> findBrandById(@PathVariable Long id) {
        final CarBrand carBrand;
        try {
            carBrand = brandService.findBrandById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carBrand);
    }

    @Operation(
            summary = "Запись бренда автомобиля",
            description = "Записать новый бренд автомобиля в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное сохранение бренда автомобиля в БД",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CarBrand.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "405", description = "Не введено название бренда автомобиля",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PostMapping
    public ResponseEntity<CarBrand> addNewBrand(@RequestBody CarBrand carBrand) {
        CarBrand brand;
        try {
            brand = brandService.saveBrand(carBrand);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(brand);
    }

    @Operation(
            summary = "Удаление бренда автомобиля",
            description = "Удаление бренда автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Удаление бренда автомобиля прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CarBrand.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Бренд для удаления не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<CarBrand> deleteBrandById(@PathVariable Long id) {
        CarBrand carBrand;
        try {
            carBrand = brandService.deleteBrandById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carBrand);
    }

    @Operation(
            summary = "Изменение бренда автомобиля",
            description = "Изменение бренда автомобиля по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Изменение бренда автомобиля прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CarBrand.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Бренд для изменения не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "405", description = "Не введено название бренда автомобиля",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PutMapping
    public ResponseEntity<CarBrand> updateBrandById(@RequestBody CarBrand carBrand) {
        CarBrand brand;
        try {
            brand = brandService.updateBrandById(carBrand);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(brand);
    }
}
