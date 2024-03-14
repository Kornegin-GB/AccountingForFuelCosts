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
import ru.fsv67.models.itinerarySheet.AddressView;
import ru.fsv67.models.itinerarySheet.FuelRecord;
import ru.fsv67.models.itinerarySheet.FuelRecordView;
import ru.fsv67.models.itinerarySheet.ItinerarySheet;
import ru.fsv67.services.car.CarService;
import ru.fsv67.services.driver.DriverService;
import ru.fsv67.services.itinerarySheet.AddressService;
import ru.fsv67.services.itinerarySheet.FuelRecordService;
import ru.fsv67.services.itinerarySheet.ItinerarySheetService;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/itinerarySheet/itinerary-sheet-create-screen.fxml")
public class ItinerarySheetCreateScreenController implements Initializable {
    private final GeneralMethodsItinerarySheetScreen generalMethodsItinerarySheetScreen;
    private final FxWeaver fxWeaver;
    private final DriverService driverService;
    private final CarService carService;
    private final ItinerarySheetService itinerarySheetService;
    private final AddressService addressService;
    private final FuelRecordService fuelRecordService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    List<Car> carList;
    List<Driver> driverList;
    Car car;
    ItinerarySheet lastItinerarySheet;

    List<FuelRecordView> fuelRecordViewList;
    List<AddressView> addressViewList;

    @FXML
    private JFXTextField numberItinerarySheet;
    @FXML
    private DateTimePicker exitDateTime;
    @FXML
    private DateTimePicker returnDateTime;
    @FXML
    private JFXComboBox<String> comboCarList;
    @FXML
    private JFXComboBox<String> comboDriverList;
    @FXML
    private Label initialBalanceField;
    @FXML
    private JFXTextField initialBalanceFieldCalc;
    @FXML
    private Label finalBalanceField;
    @FXML
    private JFXTextField finalBalanceFieldCalc;
    @FXML
    private JFXTextField consumptionOnNorm;
    @FXML
    private JFXTextField consumptionOnFact;
    @FXML
    private JFXTextField exitOdometer;
    @FXML
    private JFXTextField returnOdometer;
    @FXML
    private Label amountRefueledFuel;
    @FXML
    private JFXTextField economyFuel;
    @FXML
    private JFXTextField burnoutFuel;
    @FXML
    private Label mileageField;
    @FXML
    private JFXTreeTableView<AddressView> tableRouteCar;
    @FXML
    private JFXTextField departureAddressField;
    @FXML
    private JFXTextField destinationAddressField;
    @FXML
    private JFXButton onClickAddStringButton;
    @FXML
    private JFXTreeTableView<FuelRecordView> tableFuelRecord;

    /**
     * Метод обработка после выбора автомобиля из списка
     */
    @FXML
    void afterSelectCar() {
        String selectedItem = comboCarList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            car = findCarSelected(selectedItem);
            lastItinerarySheet = itinerarySheetService.getLastItinerarySheet(car.getId());
            if (lastItinerarySheet.getDocumentNumber() != null) {
                initialBalanceField.setText(lastItinerarySheet.getFinalBalance().toString());
                initialBalanceFieldCalc.setText(lastItinerarySheet.getFinalBalance().toString());
                exitOdometer.setText(lastItinerarySheet.getReturnOdometer().toString());
            } else {
                initialBalanceField.setText("0.0");
                initialBalanceFieldCalc.setText("0.0");
                exitOdometer.setText(car.getInitialOdometer().toString());
            }
        }
    }

    /**
     * Метод обработки после ввода одометра возвращения
     */
    @FXML
    void inputTextFieldCompleted() {
        if (comboCarList.getSelectionModel().getSelectedItem() != null) {
            if (!exitOdometer.getText().isEmpty() && !returnOdometer.getText().isEmpty()) {
                int mileage = Integer.parseInt(returnOdometer.getText()) - Integer.parseInt(exitOdometer.getText());
                mileageField.setText(String.valueOf(mileage));
            }
        }
    }

    /**
     * Обработка при выборе строки маршрута
     */
    @FXML
    void selectTableRowAddress() {
        TreeItem<AddressView> selectedItem = tableRouteCar.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            departureAddressField.setText(selectedItem.getValue().getDepartureAddress().getValue());
            destinationAddressField.setText(selectedItem.getValue().getDestinationAddress().getValue());
            onClickAddStringButton.setText("Обновить строку");
        }
    }

    /**
     * Добавление строки маршрута в таблицу маршрута
     */
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

    /**
     * Удаление строки маршрута
     */
    @FXML
    void onClickDeleteStringAddress() {
        if (tableRouteCar.getSelectionModel().getSelectedItem() != null) {
            addressViewList.removeIf(addressView -> Objects.equals(addressView.getNumberLine(),
                    tableRouteCar.getSelectionModel().getSelectedItem().getValue().getNumberLine()));
        }
        reloadTableAddresses();
    }

    /**
     * Обновление экрана таблицы маршрута
     */
    void reloadTableAddresses() {
        if (!addressViewList.isEmpty()) {
            enterDataRouteCarTable();
            destinationAddressField.setText("");
            departureAddressField.setText("");
            departureAddressField.setText(addressViewList.getLast().getDestinationAddress().getValue());
            onClickAddStringButton.setText("Добавить строку");
        }
    }

    /**
     * Добавление заправки
     */
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

    /**
     * Суммирование количества залитого топлива при добавлении заправки
     */
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

    /**
     * Удаление заправки
     */
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

    /**
     * Закрыть экран маршрутного листа
     */
    @FXML
    void onClickExit() {
        Stage stage = (Stage) numberItinerarySheet.getScene().getWindow();
        stage.close();
    }

    /**
     * Запись маршрутного листа в БД
     */
    @FXML
    void onClickSave() {
        if (comboCarList.getSelectionModel().getSelectedItem() != null
                && comboDriverList.getSelectionModel().getSelectedItem() != null
                && !addressViewList.isEmpty()) {
            List<ItinerarySheet> itinerarySheetList = itinerarySheetService.getItinerarySheetListByCarId(car.getId());
            createNumberItinerarySheet(car, itinerarySheetList);
            itinerarySheetService.saveItinerarySheet(
                    new ItinerarySheet(
                            itinerarySheetList.size() + 1,
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

    /**
     * Метод получения всех данных водителя при его выборе из списка
     *
     * @return Выбранный водитель
     */
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

    /**
     * Расчет расхода ГСМ в маршрутном листе
     */
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

    /**
     * Пересчет ГСМ в случае изменения
     */
    @FXML
    void onClickRecalculation() {
        calculate();
        generalMethodsItinerarySheetScreen.burnoutOrEconomy(economyFuel, burnoutFuel, consumptionOnFact, consumptionOnNorm);
    }

    /**
     * Расчет остатков топлива
     */
    void calculate() {
        double finalBalance = Math.ceil((Double.parseDouble(initialBalanceField.getText()) +
                Double.parseDouble(amountRefueledFuel.getText()) -
                Double.parseDouble(consumptionOnFact.getText())) * 100) / 100;

        finalBalanceField.setText(Double.toString(finalBalance));
        finalBalanceFieldCalc.setText(Double.toString(finalBalance));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addressViewList = new ArrayList<>();
        fuelRecordViewList = new ArrayList<>();

        generalMethodsItinerarySheetScreen.createTableRouteCar(tableRouteCar);
        enterDataRouteCarTable();

        generalMethodsItinerarySheetScreen.createTableFuelRecord(tableFuelRecord);
        enterDataFuelRecordTable();

        carList = carService.getCarList();
        driverList = driverService.getDriverList();

        comboCarList.setItems(getObservableList(carService.mapToCarStringList(carList)));
        comboDriverList.setItems(getObservableList(driverService.mapToDriverStringList(driverList)));

        exitDateTime.setTime(LocalDateTime.parse(LocalDate.now() + " 09:00", formatter));
        returnDateTime.setTime(LocalDateTime.parse(LocalDate.now() + " 18:00", formatter));
    }

    @FXML
    void onClickUpdateFuel(MouseEvent event) {
        if (event.getClickCount() == 2 && tableFuelRecord.getSelectionModel().getSelectedItem() != null) {
            generalMethodsItinerarySheetScreen.updateFuel(tableFuelRecord, fuelRecordViewList);
            enterDataFuelRecordTable();
            sumQuantityFuel();
        }
    }

    private ObservableList<String> getObservableList(List<String> stringList) {
        return FXCollections.observableArrayList(stringList);
    }

    private Car findCarSelected(String selectCar) {
        String[] regNumberList = selectCar.split(" ");
        String regNumber = regNumberList[regNumberList.length - 1];
        return carList.stream()
                .filter(searchCar -> Objects.equals(searchCar.getRegistrationNumber(), regNumber))
                .findFirst().orElseThrow(null);
    }

    /**
     * Формирование номера маршрутного листа
     *
     * @param car                автомобиль
     * @param itinerarySheetList список маршрутных листов
     */
    private void createNumberItinerarySheet(Car car, List<ItinerarySheet> itinerarySheetList) {
        Integer seriesItinerarySheet = car.getSeriesItinerarySheet();
        numberItinerarySheet.setText(seriesItinerarySheet + "-" + (itinerarySheetList.size() + 1));
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
