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
import ru.fsv67.models.DriversLicense;
import ru.fsv67.services.DriversLicenseService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers/license")
@Tag(name = "Водительское удостоверение", description = "Операции с водительскими удостоверениями")
public class DriversLicenseController {
    private final DriversLicenseService driversLicenseService;

    @Operation(
            summary = "Получение списка водительских удостоверений",
            description = "Получение всего списка водительских удостоверений",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное получение списка водительских удостоверений",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = DriversLicense.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список водительских удостоверений пуст.",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping
    public ResponseEntity<List<DriversLicense>> findDriversLicenseAll() {
        final List<DriversLicense> driversLicenseList;
        try {
            driversLicenseList = driversLicenseService.findDriversLicenseAll();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driversLicenseList);
    }

    @Operation(
            summary = "Получение водительского удостоверения",
            description = "Получение водительского удостоверения по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение водительского удостоверения",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = DriversLicense.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Водительское удостоверение с искомым идентификатором не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DriversLicense> findDriversLicenseById(@PathVariable Long id) {
        final DriversLicense driversLicense;
        try {
            driversLicense = driversLicenseService.findDriversLicenseById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driversLicense);
    }

    @Operation(
            summary = "Запись водительского удостоверения",
            description = "Записать новое водительское удостоверение в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Успешное сохранение водительского удостоверения в БД",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = DriversLicense.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "405", description = "Не введены данные водительского удостоверения",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PostMapping
    public ResponseEntity<DriversLicense> saveDriversLicense(@RequestBody DriversLicense newDriversLicense) {
        DriversLicense driversLicense;
        try {
            driversLicense = driversLicenseService.saveDriversLicense(newDriversLicense, true);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(driversLicense);
    }

    @Operation(
            summary = "Удаление водительского удостоверения",
            description = "Удаление водительского удостоверения по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Удаление водительского удостоверения прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = DriversLicense.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Водительское удостоверение для удаления не найдено", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<DriversLicense> deleteDriversLicense(@PathVariable Long id) {
        DriversLicense driversLicense;
        try {
            driversLicense = driversLicenseService.deleteDriversLicense(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driversLicense);
    }

    @Operation(
            summary = "Изменение водительского удостоверения",
            description = "Изменение водительского удостоверения по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Изменение водительского удостоверения прошло успешно",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = DriversLicense.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Водительское удостоверение для изменения не найден", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "405",
                            description = "Не все данные водительского удостоверения введены", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @PutMapping
    public ResponseEntity<DriversLicense> updateDriversLicense(@RequestBody DriversLicense updateDriversLicense) {
        DriversLicense driversLicense;
        try {
            driversLicense = driversLicenseService.updateDriversLicense(updateDriversLicense);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(driversLicense);
    }
}
