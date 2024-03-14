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
import ru.fsv67.models.Driver;
import ru.fsv67.services.DriverService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
@Tag(name = "Водитель", description = "Операции с водителями")
public class DriverController {
    private final DriverService driverService;

    @Operation(
            summary = "Получение списка водителей",
            description = "Получение всего списка водителей",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное получение списка водителей",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = Driver.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список водителей пуст.",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping
    public ResponseEntity<List<Driver>> findDriverAll() {
        final List<Driver> driverList;
        try {
            driverList = driverService.findDriverAll();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driverList);
    }

    @Operation(
            summary = "Получение водителя",
            description = "Получение водителя по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение водителя",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = Driver.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Водитель с искомым идентификатором не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Driver> findDriverById(@PathVariable Long id) {
        final Driver driverDTO;
        try {
            driverDTO = driverService.findDriverById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }

    @Operation(
            summary = "Запись водителя",
            description = "Записать новое водителя в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Успешное сохранение водителя в БД",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = Driver.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "405", description = "Не введены данные водителя",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PostMapping
    public ResponseEntity<Driver> saveDriver(@RequestBody Driver newDriverDTO) {
        Driver driver;
        try {
            driver = driverService.saveDriver(newDriverDTO, true);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @Operation(
            summary = "Удаление водителя",
            description = "Удаление водителя по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Удаление водителя прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = Driver.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Водитель для удаления не найдено", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable Long id) {
        Driver driver;
        try {
            driver = driverService.deleteDriver(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @Operation(
            summary = "Изменение данных водителя",
            description = "Изменение данных водителя по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Изменение данных водителя прошло успешно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404",
                            description = "Водитель для изменения данных не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "405",
                            description = "Не все данные водителя введены", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @PutMapping
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver updateDriverDTO) {
        Driver driverDTO;
        try {
            driverDTO = driverService.updateDriver(updateDriverDTO);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driverDTO);
    }
}
