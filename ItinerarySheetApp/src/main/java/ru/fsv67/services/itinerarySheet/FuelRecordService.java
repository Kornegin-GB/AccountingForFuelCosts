package ru.fsv67.services.itinerarySheet;

import javafx.beans.property.SimpleLongProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.models.itinerarySheet.FuelRecord;
import ru.fsv67.models.itinerarySheet.FuelRecordView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FuelRecordService {
    private final FuelBrandService fuelBrandService;

    public FuelRecordView mapToFuelRecordView(FuelRecord fuelRecord) {
        return new FuelRecordView(
                fuelRecord.getRefuelingDate(),
                fuelRecord.getFuelBrand().getFuelBrandName(),
                fuelRecord.getFuelQuantity(),
                fuelRecord.getFuelPrice(),
                fuelRecord.getFuelSum()
        );
    }

    public List<FuelRecordView> mapToFuelRecordViewList(List<FuelRecordView> fuelRecordViewList) {
        long numberLine = 1L;
        for (FuelRecordView fuelRecordView : fuelRecordViewList) {
            fuelRecordView.setNumberLine(new SimpleLongProperty(numberLine++));
        }
        return fuelRecordViewList;
    }

    public List<FuelRecord> mapToFuelRecordList(List<FuelRecordView> fuelRecordViewList) {
        List<FuelRecord> fuelRecords = new ArrayList<>();
        for (FuelRecordView fuelRecordView : fuelRecordViewList) {
            fuelRecords.add(mapToFuelRecord(fuelRecordView));
        }
        return fuelRecords;
    }

    private FuelRecord mapToFuelRecord(FuelRecordView fuelRecordView) {
        return new FuelRecord(
                LocalDate.parse(fuelRecordView.getRefuelingDate().getValue(),
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                fuelBrandService.getFuelBrandList()
                        .stream()
                        .filter(fuelBrand -> Objects.equals(fuelBrand.getFuelBrandName(),
                                fuelRecordView.getFuelBrand().getValue()))
                        .findFirst().orElseThrow(null),
                fuelRecordView.getFuelQuantity().getValue(),
                fuelRecordView.getFuelPrice().getValue(),
                fuelRecordView.getFuelSum().getValue()
        );
    }

    public List<FuelRecordView> mapFuelRecordListToFuelRecordViewList(List<FuelRecord> fuelRecordList) {
        List<FuelRecordView> fuelRecordViewList = new ArrayList<>();
        for (FuelRecord fuelRecord : fuelRecordList) {
            fuelRecordViewList.add(mapToFuelRecordView(fuelRecord));
        }
        return fuelRecordViewList;
    }
}
