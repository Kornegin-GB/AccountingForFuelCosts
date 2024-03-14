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
import ru.fsv67.dto.ItinerarySheetDTO;
import ru.fsv67.services.ItinerarySheetService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/itinerary")
public class ItinerarySheetController {
    private final ItinerarySheetService itinerarySheetService;

    @Operation(
            summary = "Получение списка маршрутных листов",
            description = "Получение всего списка маршрутных листов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка маршрутных листов",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = ItinerarySheetDTO.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список маршрутных листов пуст.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Соединение с сервером не установлено.",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @GetMapping
    public ResponseEntity<List<ItinerarySheetDTO>> findAllItinerarySheet() {
        List<ItinerarySheetDTO> itinerarySheetDTOList;
        try {
            itinerarySheetDTOList = itinerarySheetService.findAllItinerarySheet();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(itinerarySheetDTOList);
    }

    @Operation(
            summary = "Получение списка маршрутных листов по идентификатору автомобиля",
            description = "Получение списка маршрутных листов по идентификатору автомобиля",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка маршрутных листов",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = ItinerarySheetDTO.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Список маршрутных листов пуст.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Соединение с сервером не установлено.",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @GetMapping("/{carId}")
    public ResponseEntity<List<ItinerarySheetDTO>> findByCarId(@PathVariable Long carId) {
        List<ItinerarySheetDTO> itinerarySheetDTOList;
        try {
            itinerarySheetDTOList = itinerarySheetService.findByCarId(carId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(itinerarySheetDTOList);
    }

    @Operation(
            summary = "Получение последнего маршрутного листа по идентификатору автомобиля",
            description = "Получение последнего маршрутного листа по идентификатору автомобиля",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение маршрутного листа",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = ItinerarySheetDTO.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404",
                            description = "Маршрутный лист по идентификатору автомобиля не найден.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Соединение с сервером не установлено.",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @GetMapping("/car/{id}")
    public ResponseEntity<ItinerarySheetDTO> findLastItinerarySheetDyCarId(@PathVariable Long id) {
        ItinerarySheetDTO itinerarySheetDTO;
        try {
            itinerarySheetDTO = itinerarySheetService.findLastItinerarySheetDyCarId(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(itinerarySheetDTO);
    }

    @Operation(
            summary = "Запись маршрутного листа",
            description = "Сохранение маршрутного листа в базу данных",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное сохранение маршрутного листа",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = ItinerarySheetDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "409", description = "Ошибка при записи маршрутного листа.", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Соединение с сервером не установлено.",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @PostMapping
    public ResponseEntity<ItinerarySheetDTO> findByCarId(@RequestBody ItinerarySheetDTO itinerarySheet) {
        ItinerarySheetDTO itinerarySheetDTO;
        try {
            itinerarySheetDTO = itinerarySheetService.saveItinerarySheet(itinerarySheet, true);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(itinerarySheetDTO);
    }

    @Operation(
            summary = "Обновление маршрутного листа",
            description = "Обновление маршрутного листа в базе данных",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное обновление маршрутного листа",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = ItinerarySheetDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "409", description = "Ошибка при обновлении маршрутного листа.",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Соединение с сервером не установлено.",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @PutMapping
    public ResponseEntity<ItinerarySheetDTO> updateItinerarySheet(@RequestBody ItinerarySheetDTO itinerarySheet) {
        ItinerarySheetDTO itinerarySheetDTO;
        try {
            itinerarySheetDTO = itinerarySheetService.updateItinerarySheetDTO(itinerarySheet);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(itinerarySheetDTO);
    }

    @Operation(
            summary = "Удаление маршрутного листа",
            description = "Удаление маршрутного листа в базе данных",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное удаление маршрутного листа",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = ItinerarySheetDTO.class)
                                    )
                            }),
                    @ApiResponse(responseCode = "404", description = "Удаляемый маршрутный лист не найден.",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Соединение с сервером не установлено.",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ItinerarySheetDTO> deleteItinerarySheet(@PathVariable Long id) {
        ItinerarySheetDTO itinerarySheetDTO;
        try {
            itinerarySheetDTO = itinerarySheetService.deleteItinerarySheetDTO(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(itinerarySheetDTO);
    }
}
