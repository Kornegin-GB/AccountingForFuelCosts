package ru.fsv67.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fsv67.models.fuel.FuelBrandEntity;
import ru.fsv67.services.FuelBrandService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itinerary/fuel")
public class FuelBrandController {
    private final FuelBrandService fuelBrandService;

    @Operation(
            summary = "Получение списка брендов топлива",
            description = "Получение всего списка брендов топлива",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка брендов",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = FuelBrandEntity.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список брендов пуст.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping
    public ResponseEntity<List<FuelBrandEntity>> findAllFuelBrand() {
        List<FuelBrandEntity> fuelBrandEntityList;
        try {
            fuelBrandEntityList = fuelBrandService.findFuelBrandAll();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(fuelBrandEntityList);
    }

    @Operation(
            summary = "Получение бренда топлива по идентификатору",
            description = "Получение бренда топлива по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение бренда",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = FuelBrandEntity.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Бренд с искомым идентификатором не найден.",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<FuelBrandEntity> findFuelBrandById(@PathVariable Long id) {
        FuelBrandEntity fuelBrandEntity;
        try {
            fuelBrandEntity = fuelBrandService.findFuelBrandById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(fuelBrandEntity);
    }

    @Operation(
            summary = "Запись бренда топлива",
            description = "Запись бренда топлива",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Запись бренда топлива в БД",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = FuelBrandEntity.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "409", description = "Не задано название бренда",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PostMapping()
    public ResponseEntity<FuelBrandEntity> saveFuelBrand(@RequestBody FuelBrandEntity newFuelBrandEntity) {
        FuelBrandEntity fuelBrandEntity;
        try {
            fuelBrandEntity = fuelBrandService.saveFuelBrand(newFuelBrandEntity);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(fuelBrandEntity);
    }
}
