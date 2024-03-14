package ru.fsv67.controllers.car;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.fsv67.AppProperties;
import ru.fsv67.controllers.MainScreenController;
import ru.fsv67.models.car.CarView;
import ru.fsv67.services.car.CarService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/car/car-list.fxml")
@EnableConfigurationProperties(AppProperties.class)
public class CarListScreenController implements Initializable {
    private final CarService carService;
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;

    @FXML
    private JFXTreeTableView<CarView> tableView;
    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    @FXML
    void onSelectingLine() {
        isDisabledButton();
    }

    void isDisabledButton() {
        buttonUpdate.setDisable(tableView.getSelectionModel().getSelectedIndex() < 0);
        buttonDelete.setDisable(tableView.getSelectionModel().getSelectedIndex() < 0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        isDisabledButton();

        JFXTreeTableColumn<CarView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(-1);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<CarView, String> registrationNumber = new JFXTreeTableColumn<>("Регистрационный номер");
        registrationNumber.setMinWidth(-1);
        registrationNumber.setCellValueFactory(param -> param.getValue().getValue().getRegistrationNumber());
        registrationNumber.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<CarView, String> vin = new JFXTreeTableColumn<>("VIN номер");
        vin.setMinWidth(-1);
        vin.setCellValueFactory(param -> param.getValue().getValue().getVin());
        vin.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<CarView, Double> fuelConsumptionRate = new JFXTreeTableColumn<>("Норма расхода топлива");
        fuelConsumptionRate.setMinWidth(-1);
        fuelConsumptionRate.setCellValueFactory(param -> param.getValue().getValue().getFuelConsumptionRate().asObject());
        fuelConsumptionRate.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<CarView, Integer> seriesItinerarySheet = new JFXTreeTableColumn<>("Серия маршрутных листов");
        seriesItinerarySheet.setMinWidth(-1);
        seriesItinerarySheet.setCellValueFactory(param -> param.getValue().getValue().getSeriesItinerarySheet().asObject());
        seriesItinerarySheet.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<CarView, Integer> initialOdometer = new JFXTreeTableColumn<>("Начальный одометр");
        initialOdometer.setMinWidth(-1);
        initialOdometer.setCellValueFactory(param -> param.getValue().getValue().getInitialOdometer().asObject());
        initialOdometer.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<CarView, String> carModel = new JFXTreeTableColumn<>("Модель");
        carModel.setMinWidth(-1);
        carModel.setCellValueFactory(param -> param.getValue().getValue().getCarModel());
        carModel.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<CarView, String> carBrand = new JFXTreeTableColumn<>("Бренд");
        carBrand.setMinWidth(100);
        carBrand.isReorderable();
        carBrand.setCellValueFactory(param -> param.getValue().getValue().getCarBrand());
        carBrand.setStyle("-fx-alignment: center;");

        tableView.getColumns().setAll(
                List.of(numberLine, carBrand, carModel, registrationNumber, vin, seriesItinerarySheet,
                        initialOdometer, fuelConsumptionRate)
        );

        entreDataIntoTable();
    }

    void entreDataIntoTable() {
        final TreeItem<CarView> carTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                carService.mapToCarViewList(carService.getCarList())
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableView.setRoot(carTreeItem);
        tableView.setShowRoot(false);
    }

    @FXML
    void onClickCreate() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(CarCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoTable();
    }

    @FXML
    void onClickExit() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(MainScreenController.class));
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.setResizable(false);
        stage.show();
        Stage windowStage = (Stage) buttonDelete.getScene().getWindow();
        windowStage.close();
    }

    @FXML
    void onClickDelete() {
        Long carId = tableView.getSelectionModel().getSelectedItem().getValue().getId().getValue();
        carService.deleteCarData(carId);
        entreDataIntoTable();
        isDisabledButton();
    }

    @FXML
    void onClickUpdate() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            fxWeaver.loadController(CarUpdateScreenController.class).carView =
                    tableView.getSelectionModel().getSelectedItem().getValue();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(CarUpdateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoTable();
        isDisabledButton();
    }
}
