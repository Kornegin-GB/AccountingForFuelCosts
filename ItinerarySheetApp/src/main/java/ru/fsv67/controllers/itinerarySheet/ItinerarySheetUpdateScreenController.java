package ru.fsv67.controllers.itinerarySheet;

import com.browniebytes.javafx.control.DateTimePicker;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.fsv67.models.car.Car;
import ru.fsv67.models.driver.Driver;
import ru.fsv67.models.itinerarySheet.*;
import ru.fsv67.services.car.CarService;
import ru.fsv67.services.driver.DriverService;
import ru.fsv67.services.itinerarySheet.AddressService;
import ru.fsv67.services.itinerarySheet.FuelRecordService;
import ru.fsv67.services.itinerarySheet.ItinerarySheetService;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/itinerarySheet/itinerary-sheet-update-screen.fxml")
public class ItinerarySheetUpdateScreenController implements Initializable {
    private final GeneralMethodsItinerarySheetScreen generalMethodsItinerarySheetScreen;
    private final DriverService driverService;
    private final CarService carService;
    private final FxWeaver fxWeaver;
    private final AddressService addressService;
    private final FuelRecordService fuelRecordService;
    private final ItinerarySheetService itinerarySheetService;
    public ItinerarySheetView itinerarySheetView;
    public List<Car> carList;
    public List<Driver> driverList;
    public List<FuelRecordView> fuelRecordViewList;
    public List<AddressView> addressViewList;
    private Car car;

    @FXML
    private Label amountRefueledFuel;

    @FXML
    private JFXTextField burnoutFuel;

    @FXML
    private JFXComboBox<String> comboCarList;

    @FXML
    private JFXComboBox<String> comboDriverList;

    @FXML
    private JFXTextField consumptionOnFact;

    @FXML
    private JFXTextField consumptionOnNorm;

    @FXML
    private JFXTextField departureAddressField;

    @FXML
    private JFXTextField destinationAddressField;

    @FXML
    private JFXTextField economyFuel;

    @FXML
    private DateTimePicker exitDateTime;

    @FXML
    private JFXTextField exitOdometer;

    @FXML
    private Label finalBalanceField;

    @FXML
    private JFXTextField finalBalanceFieldCalc;

    @FXML
    private Label initialBalanceField;

    @FXML
    private JFXTextField initialBalanceFieldCalc;

    @FXML
    private Label mileageField;

    @FXML
    private JFXTextField numberItinerarySheet;

    @FXML
    private JFXButton onClickAddStringButton;

    @FXML
    private DateTimePicker returnDateTime;

    @FXML
    private JFXTextField returnOdometer;

    @FXML
    private JFXTreeTableView<FuelRecordView> tableFuelRecord;

    @FXML
    private JFXTreeTableView<AddressView> tableRouteCar;

    @FXML
    void inputTextFieldCompleted() {
        if (comboCarList.getSelectionModel().getSelectedItem() != null) {
            if (!exitOdometer.getText().isEmpty() && !returnOdometer.getText().isEmpty()) {
                int mileage = Integer.parseInt(returnOdometer.getText()) - Integer.parseInt(exitOdometer.getText());
                mileageField.setText(String.valueOf(mileage));
            }
        }
    }

    @FXML
    void onClickAddRefueled() {
        generalMethodsItinerarySheetScreen.addRefueled(exitDateTime);
        FuelRecord fuelRecord = fxWeaver.loadController(FuelCreateScreenController.class).fuelRecord;
        if (fuelRecord != null) {
            fuelRecordViewList.add(fuelRecordService.mapToFuelRecordView(fuelRecord));
            enterDataFuelRecordTable();
            sumQuantityFuel();
        }
    }

    @FXML
    void onClickAddStringAddress() {
        if (tableRouteCar.getSelectionModel().getSelectedItem() != null) {
            AddressView selectAddressView = addressViewList.stream()
                    .filter(addressView -> Objects.equals(addressView.getNumberLine(),
                            tableRouteCar.getSelectionModel().getSelectedItem().getValue().getNumberLine()))
                    .findFirst().orElseThrow(null);
            selectAddressView.setDepartureAddress(new SimpleStringProperty(departureAddressField.getText()));
            selectAddressView.setDestinationAddress(new SimpleStringProperty(destinationAddressField.getText()));
        } else {
            if (!departureAddressField.getText().isEmpty() && !destinationAddressField.getText().isEmpty()) {
                addressViewList.add(
                        new AddressView(
                                departureAddressField.getText(),
                                destinationAddressField.getText()
                        )
                );
            }
        }
        reloadTableAddresses();
    }

    @FXML
    void onClickCalculationFuel() {
        if (comboCarList.getSelectionModel().getSelectedItem() != null && !mileageField.getText().isEmpty()) {
            Double normConsumption = car.getFuelConsumptionRate();

            double consumption =
                    Math.ceil((Double.parseDouble(mileageField.getText()) * normConsumption / 100) * 100) / 100;
            consumptionOnNorm.setText(Double.toString(consumption));
            consumptionOnFact.setText(Double.toString(consumption));

            calculate();
            generalMethodsItinerarySheetScreen.burnoutOrEconomy(economyFuel, burnoutFuel, consumptionOnFact, consumptionOnNorm);
        }
    }

    @FXML
    void onClickDeleteRefueled() {
        FuelRecordView fuelRecordView = tableFuelRecord.getSelectionModel().getSelectedItem().getValue();
        if (fuelRecordView != null) {
            fuelRecordViewList.removeIf(
                    fuelRecord -> Objects.equals(fuelRecord.getNumberLine(), fuelRecordView.getNumberLine())
            );
        }
        enterDataFuelRecordTable();
        sumQuantityFuel();
    }

    @FXML
    void onClickDeleteStringAddress() {
        if (tableRouteCar.getSelectionModel().getSelectedItem() != null) {
            addressViewList.removeIf(addressView -> Objects.equals(addressView.getNumberLine(),
                    tableRouteCar.getSelectionModel().getSelectedItem().getValue().getNumberLine()));
        }
        reloadTableAddresses();
    }

    @FXML
    void onClickExit() {
        Stage stage = (Stage) numberItinerarySheet.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickRecalculation() {
        calculate();
        generalMethodsItinerarySheetScreen.burnoutOrEconomy(economyFuel, burnoutFuel, consumptionOnFact, consumptionOnNorm);
    }

    @FXML
    void onClickSave() {
        if (comboCarList.getSelectionModel().getSelectedItem() != null
                && comboDriverList.getSelectionModel().getSelectedItem() != null
                && !addressViewList.isEmpty()) {
            itinerarySheetService.updateItinerarySheet(
                    new ItinerarySheet(
                            itinerarySheetView.getId().getValue(),
                            Integer.parseInt(itinerarySheetView.getDocumentNumber().getValue().split("-")[1]),
                            car.getSeriesItinerarySheet(),
                            LocalDateTime.parse(exitDateTime.dateTimeProperty().getValue().toString()),
                            LocalDateTime.parse(returnDateTime.dateTimeProperty().getValue().toString()),
                            car,
                            Integer.parseInt(exitOdometer.getText()),
                            Integer.parseInt(returnOdometer.getText()),
                            selectDriver(),
                            Double.parseDouble(initialBalanceField.getText()),
                            Double.parseDouble(consumptionOnFact.getText()),
                            Double.parseDouble(finalBalanceField.getText()),
                            fuelRecordService.mapToFuelRecordList(fuelRecordViewList),
                            addressService.mapToAddressList(addressViewList)
                    )
            );
            onClickExit();
        }
    }

    @FXML
    void onClickUpdateFuel(MouseEvent event) {
        if (event.getClickCount() == 2 && tableFuelRecord.getSelectionModel().getSelectedItem() != null) {
            generalMethodsItinerarySheetScreen.updateFuel(tableFuelRecord, fuelRecordViewList);
            enterDataFuelRecordTable();
            sumQuantityFuel();
        }
    }

    @FXML
    void selectTableRowAddress() {
        TreeItem<AddressView> selectedItem = tableRouteCar.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            departureAddressField.setText(selectedItem.getValue().getDepartureAddress().getValue());
            destinationAddressField.setText(selectedItem.getValue().getDestinationAddress().getValue());
            onClickAddStringButton.setText("Обновить строку");
        }
    }

    @FXML
    void onClickPullData() {
        car = findCarSelected(comboCarList.getSelectionModel().getSelectedItem());
        List<ItinerarySheet> itinerarySheetList = itinerarySheetService.getItinerarySheetListByCarId(car.getId());
        ItinerarySheet lastItinerarySheet = itinerarySheetList.stream()
                .filter(ish -> Objects.equals(ish.getDocumentNumber(),
                        Integer.parseInt(itinerarySheetView.getDocumentNumber().getValue().split("-")[1]) - 1
                ))
                .findFirst().orElse(null);
        if (lastItinerarySheet != null) {
            initialBalanceField.setText(lastItinerarySheet.getFinalBalance().toString());
            initialBalanceFieldCalc.setText(lastItinerarySheet.getFinalBalance().toString());
            exitOdometer.setText(lastItinerarySheet.getReturnOdometer().toString());
        } else {
            initialBalanceField.setText("0.0");
            initialBalanceFieldCalc.setText("0.0");
            exitOdometer.setText(car.getInitialOdometer().toString());
        }
        inputTextFieldCompleted();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generalMethodsItinerarySheetScreen.createTableFuelRecord(tableFuelRecord);
        generalMethodsItinerarySheetScreen.createTableRouteCar(tableRouteCar);

        carList = carService.getCarList();
        driverList = driverService.getDriverList();

        comboCarList.setItems(getObservableList(carService.mapToCarStringList(carList)));
        comboDriverList.setItems(getObservableList(driverService.mapToDriverStringList(driverList)));

        if (itinerarySheetView != null) {
            numberItinerarySheet.setText(itinerarySheetView.getDocumentNumber().getValue());
            comboCarList.getSelectionModel().select(itinerarySheetView.getCar().getValue());
            comboDriverList.getSelectionModel().select(itinerarySheetView.getDriver().getValue());
            exitDateTime.dateTimeProperty().setValue(
                    LocalDateTime.parse(itinerarySheetView.getDepartureDate().getValue(),
                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            );
            returnDateTime.dateTimeProperty().setValue(
                    LocalDateTime.parse(itinerarySheetView.getReturnDate().getValue(),
                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            );
            exitOdometer.setText(String.valueOf(itinerarySheetView.getExitOdometer().getValue()));
            returnOdometer.setText(String.valueOf(itinerarySheetView.getReturnOdometer().getValue()));
            mileageField.setText(
                    String.valueOf(
                            itinerarySheetView.getReturnOdometer().getValue() - itinerarySheetView.getExitOdometer().getValue()
                    )
            );
            initialBalanceField.setText(itinerarySheetView.getInitialBalance().getValue().toString());
            finalBalanceField.setText(itinerarySheetView.getFinalBalance().getValue().toString());
            initialBalanceFieldCalc.setText(initialBalanceField.getText());
            finalBalanceFieldCalc.setText(finalBalanceField.getText());

            addressViewList = itinerarySheetView.getAddressList().getValue();
            fuelRecordViewList = itinerarySheetView.getFuelRecordList().getValue();

            car = findCarSelected(comboCarList.getSelectionModel().getSelectedItem());
            enterDataRouteCarTable();
            enterDataFuelRecordTable();

            onClickCalculationFuel();
            consumptionOnFact.setText(Double.toString(itinerarySheetView.getFuelConsumption().getValue()));
            sumQuantityFuel();
            onClickRecalculation();
        }
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }

    void reloadTableAddresses() {
        if (!addressViewList.isEmpty()) {
            enterDataRouteCarTable();
            destinationAddressField.setText("");
            departureAddressField.setText("");
            departureAddressField.setText(addressViewList.getLast().getDestinationAddress().getValue());
            onClickAddStringButton.setText("Добавить строку");
        }
    }

    private void sumQuantityFuel() {
        double sum = 0.0;
        if (!fuelRecordViewList.isEmpty()) {
            for (FuelRecordView fuelRecordView : fuelRecordViewList) {
                sum += fuelRecordView.getFuelQuantity().getValue();
                amountRefueledFuel.setText(Double.toString(sum));
            }
        } else {
            amountRefueledFuel.setText(Double.toString(0.0));
        }
    }

    private Driver selectDriver() {
        String[] selectDriver = comboDriverList.getSelectionModel().getSelectedItem().split(" ");
        for (Driver driver : driverList) {
            if (driver.getLastName().equals(selectDriver[0]) &&
                    driver.getFirstName().equals(selectDriver[1]) &&
                    driver.getParentName().equals(selectDriver[2])) {
                return driver;
            }
        }
        return null;
    }

    void calculate() {
        double finalBalance = Math.ceil((Double.parseDouble(initialBalanceField.getText()) +
                Double.parseDouble(amountRefueledFuel.getText()) -
                Double.parseDouble(consumptionOnFact.getText())) * 100) / 100;

        finalBalanceField.setText(Double.toString(finalBalance));
        finalBalanceFieldCalc.setText(Double.toString(finalBalance));
    }

    private Car findCarSelected(String selectCar) {
        String[] regNumberList = selectCar.split(" ");
        String regNumber = regNumberList[regNumberList.length - 1];
        return carList.stream()
                .filter(searchCar -> Objects.equals(searchCar.getRegistrationNumber(), regNumber))
                .findFirst().orElseThrow(null);
    }

    private void enterDataRouteCarTable() {
        final TreeItem<AddressView> addressTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                addressService.setNumberLine(addressViewList)
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableRouteCar.setRoot(addressTreeItem);
        tableRouteCar.setShowRoot(false);
    }

    private void enterDataFuelRecordTable() {
        final TreeItem<FuelRecordView> fuelRecordViewTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                fuelRecordService.mapToFuelRecordViewList(fuelRecordViewList)
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableFuelRecord.setRoot(fuelRecordViewTreeItem);
        tableFuelRecord.setShowRoot(false);
    }
}
