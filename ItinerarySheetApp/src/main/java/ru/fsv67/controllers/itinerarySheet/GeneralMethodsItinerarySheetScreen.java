package ru.fsv67.controllers.itinerarySheet;

import com.browniebytes.javafx.control.DateTimePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;
import ru.fsv67.AppProperties;
import ru.fsv67.models.itinerarySheet.AddressView;
import ru.fsv67.models.itinerarySheet.FuelRecordView;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GeneralMethodsItinerarySheetScreen {
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;

    void addRefueled(DateTimePicker exitDateTime) {
        fxWeaver.loadController(FuelCreateScreenController.class).refuelingDate =
                exitDateTime.dateTimeProperty().getValue().toLocalDate();
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(FuelCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
    }

    void updateFuel(JFXTreeTableView<FuelRecordView> tableFuelRecord, List<FuelRecordView> fuelRecordViewList) {
        fxWeaver.loadController(FuelUpdateScreenController.class).fuelRecordView =
                tableFuelRecord.getSelectionModel().getSelectedItem().getValue();
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(FuelUpdateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        FuelRecordView fuelRecord = fxWeaver.loadController(FuelUpdateScreenController.class).fuelRecordView;
        if (fuelRecord != null) {
            FuelRecordView searchFuelRecordView = fuelRecordViewList
                    .stream()
                    .filter(fuelRecordView -> Objects.equals(fuelRecordView.getNumberLine(),
                            fuelRecord.getNumberLine()))
                    .findFirst()
                    .orElseThrow(null);
            searchFuelRecordView.setFuelPrice(fuelRecord.getFuelPrice());
            searchFuelRecordView.setFuelBrand(fuelRecord.getFuelBrand());
            searchFuelRecordView.setFuelSum(fuelRecord.getFuelSum());
            searchFuelRecordView.setFuelQuantity(fuelRecord.getFuelQuantity());
            searchFuelRecordView.setRefuelingDate(fuelRecord.getRefuelingDate());
            searchFuelRecordView.setNumberLine(fuelRecord.getNumberLine());
        }
    }

    void createTableRouteCar(JFXTreeTableView<AddressView> tableRouteCar) {
        JFXTreeTableColumn<AddressView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(20);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<AddressView, String> departureAddress = new JFXTreeTableColumn<>("Адрес отправления");
        departureAddress.setMinWidth(200);
        departureAddress.setCellValueFactory(param -> param.getValue().getValue().getDepartureAddress());
        departureAddress.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<AddressView, String> destinationAddress = new JFXTreeTableColumn<>("Адрес назначения");
        destinationAddress.setMinWidth(200);
        destinationAddress.setCellValueFactory(param -> param.getValue().getValue().getDestinationAddress());
        destinationAddress.setStyle("-fx-alignment: center;");

        tableRouteCar.getColumns().setAll(List.of(numberLine, departureAddress, destinationAddress));
    }

    void createTableFuelRecord(JFXTreeTableView<FuelRecordView> tableFuelRecord) {
        JFXTreeTableColumn<FuelRecordView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(-1);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<FuelRecordView, String> refuelingDate = new JFXTreeTableColumn<>("Дата заправки");
        refuelingDate.setMinWidth(100);
        refuelingDate.setCellValueFactory(param -> param.getValue().getValue().getRefuelingDate());
        refuelingDate.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<FuelRecordView, String> fuelBrand = new JFXTreeTableColumn<>("Бренд топлива");
        fuelBrand.setMinWidth(120);
        fuelBrand.setCellValueFactory(param -> param.getValue().getValue().getFuelBrand());
        fuelBrand.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<FuelRecordView, String> fuelQuantity = new JFXTreeTableColumn<>("Количество топлива");
        fuelQuantity.setMinWidth(150);
        fuelQuantity.setCellValueFactory(param -> param.getValue().getValue().getFuelQuantity().asString());
        fuelQuantity.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<FuelRecordView, String> fuelPrice = new JFXTreeTableColumn<>("Цена");
        fuelPrice.setMinWidth(40);
        fuelPrice.setCellValueFactory(param -> param.getValue().getValue().getFuelPrice().asString());
        fuelPrice.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<FuelRecordView, String> fuelSum = new JFXTreeTableColumn<>("Сумма");
        fuelSum.setMinWidth(50);
        fuelSum.setCellValueFactory(param -> param.getValue().getValue().getFuelSum().asString());
        fuelSum.setStyle("-fx-alignment: center;");

        tableFuelRecord.getColumns().setAll(List.of(numberLine, refuelingDate, fuelBrand, fuelQuantity, fuelPrice, fuelSum));
    }

    /**
     * Расчет пережога или экономии
     */
    void burnoutOrEconomy(JFXTextField economyFuel, JFXTextField burnoutFuel, JFXTextField consumptionOnFact, JFXTextField consumptionOnNorm) {
        double fact = Double.parseDouble(consumptionOnFact.getText());
        double norm = Double.parseDouble(consumptionOnNorm.getText());
        if (fact > norm) {
            economyFuel.setText("0.0");
            double result = fact - norm;
            burnoutFuel.setText(Double.toString(result));
        }
        if (fact < norm) {
            burnoutFuel.setText("0.0");
            double result = norm - fact;
            economyFuel.setText(Double.toString(result));
        }
        if (fact == norm) {
            burnoutFuel.setText("0.0");
            economyFuel.setText("0.0");
        }
    }
}
