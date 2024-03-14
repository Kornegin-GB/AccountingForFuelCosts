package ru.fsv67.controllers.driver;

import com.jfoenix.controls.JFXButton;
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
import ru.fsv67.models.driver.Driver;
import ru.fsv67.models.driver.DriverView;
import ru.fsv67.models.driver.DriversLicenseView;
import ru.fsv67.services.driver.DriverLicenseService;
import ru.fsv67.services.driver.DriverService;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/driver/driver-list.fxml")
@EnableConfigurationProperties(AppProperties.class)
public class DriverListScreenController implements Initializable {
    private final DriverService driverService;
    private final DriverLicenseService driverLicenseService;
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;
    List<Driver> driverList;

    @FXML
    private Button exitButton;

    @FXML
    private JFXButton addDriveLicense;

    @FXML
    private JFXButton deleteDrive;

    @FXML
    private JFXButton deleteDriveLicense;

    @FXML
    private JFXTreeTableView<DriverView> tableViewDriver;

    @FXML
    private JFXTreeTableView<DriversLicenseView> tableViewDriverLicense;

    @FXML
    private JFXButton updateDrive;

    @FXML
    private JFXButton updateDriveLicense;


    @FXML
    void onClickAddDriverLicense() {
        Driver driver = driverList.stream()
                .filter(searchDriver -> Objects.equals(
                        searchDriver.getId(),
                        tableViewDriver.getSelectionModel().getSelectedItem().getValue().getId().getValue()))
                .findFirst().orElseThrow(null);
        if (tableViewDriver.getSelectionModel().getSelectedItem() != null) {
            fxWeaver.loadController(DriverLicenseCreateScreenController.class).driverId = driver.getId();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(DriverLicenseCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoDriversTable();
        entreDataIntoDriverLicenseTable();
        isDisabledButton();
        isDisabledButtonDriverLicense();
    }

    @FXML
    void onClickCreateDriver() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(DriverCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoDriversTable();
        entreDataIntoDriverLicenseTable();
    }

    @FXML
    void onClickUpdateDriver() {
        Driver driver = driverList.stream()
                .filter(searchDriver -> Objects.equals(
                        searchDriver.getId(),
                        tableViewDriver.getSelectionModel().getSelectedItem().getValue().getId().getValue()))
                .findFirst().orElseThrow(null);
        if (tableViewDriver.getSelectionModel().getSelectedItem() != null) {
            fxWeaver.loadController(DriverUpdateScreenController.class).driver = driver;
        }
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(DriverUpdateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoDriversTable();
        entreDataIntoDriverLicenseTable();
        isDisabledButton();
        isDisabledButtonDriverLicense();
    }

    @FXML
    void onClickUpdateDriverLicense() {
        if (tableViewDriver.getSelectionModel().getSelectedItem() != null) {
            fxWeaver.loadController(DriverLicenseUpdateScreenController.class).driversLicenseView =
                    tableViewDriverLicense.getSelectionModel().getSelectedItem().getValue();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(DriverLicenseUpdateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        entreDataIntoDriversTable();
        entreDataIntoDriverLicenseTable();
        isDisabledButton();
        isDisabledButtonDriverLicense();
    }

    @FXML
    void onClickDeleteDriver() {
        DriverView driverView = tableViewDriver.getSelectionModel().getSelectedItem().getValue();
        driverService.deleteDriver(driverView.getId().getValue());
        entreDataIntoDriversTable();
        createTableDriverLicense();
        isDisabledButton();
        isDisabledButtonDriverLicense();
    }

    @FXML
    void onClickDeleteDriverLicense() {
        DriversLicenseView driversLicenseView =
                tableViewDriverLicense.getSelectionModel().getSelectedItem().getValue();
        driverLicenseService.deleteDriverLicense(driversLicenseView.getId().getValue());
        driverList = driverService.getDriverList();
        createTableDriverLicense();
        isDisabledButton();
        isDisabledButtonDriverLicense();
    }


    @FXML
    void onClickExit() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(MainScreenController.class));
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.setResizable(false);
        stage.show();
        Stage windowStage = (Stage) exitButton.getScene().getWindow();
        windowStage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTableDrivers();
        createTableDriverLicense();

        isDisabledButton();
        isDisabledButtonDriverLicense();

        entreDataIntoDriversTable();
    }

    void createTableDrivers() {
        JFXTreeTableColumn<DriverView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(-1);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<DriverView, String> fio = new JFXTreeTableColumn<>("Фамилия Имя Отчество");
        fio.setMinWidth(-1);
        fio.setCellValueFactory(param -> param.getValue().getValue().getFio());
        fio.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<DriverView, String> snils = new JFXTreeTableColumn<>("СНИЛС");
        snils.setMinWidth(-1);
        snils.setCellValueFactory(param -> param.getValue().getValue().getSnils());
        snils.setStyle("-fx-alignment: center;");

        tableViewDriver.getColumns().setAll(
                List.of(numberLine, fio, snils)
        );
    }

    void createTableDriverLicense() {

        JFXTreeTableColumn<DriversLicenseView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(-1);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<DriversLicenseView, String> numberDriversLicense =
                new JFXTreeTableColumn<>("Номер серия ВУ");
        numberDriversLicense.setMinWidth(-1);
        numberDriversLicense.setCellValueFactory(param -> param.getValue().getValue().getNumberDriversLicense());
        numberDriversLicense.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<DriversLicenseView, String> categories = new JFXTreeTableColumn<>("Открытые категории");
        categories.setMinWidth(-1);
        categories.setCellValueFactory(param -> param.getValue().getValue().getCategories());
        categories.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<DriversLicenseView, String> dateOfIssue = new JFXTreeTableColumn<>("Дата выдачи");
        dateOfIssue.setMinWidth(-1);
        dateOfIssue.setCellValueFactory(param -> param.getValue().getValue().getDateOfIssue());
        dateOfIssue.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<DriversLicenseView, String> dateOfValidity = new JFXTreeTableColumn<>("Действительны до");
        dateOfValidity.setMinWidth(-1);
        dateOfValidity.setCellValueFactory(param -> param.getValue().getValue().getDateOfValidity());
        dateOfValidity.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<DriversLicenseView, String> isReally = new JFXTreeTableColumn<>("Действительно");
        isReally.setMinWidth(-1);
        isReally.setCellValueFactory(param -> param.getValue().getValue().getIsReally());
        isReally.setStyle("-fx-alignment: center;");

        tableViewDriverLicense.getColumns().setAll(
                List.of(numberLine, numberDriversLicense, categories, dateOfIssue, dateOfValidity, isReally)
        );

        entreDataIntoDriverLicenseTable();
    }

    void entreDataIntoDriversTable() {
        driverList = driverService.getDriverList();
        final TreeItem<DriverView> driverTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                driverService.mapToDriverViewList(driverList)
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableViewDriver.setRoot(driverTreeItem);
        tableViewDriver.setShowRoot(false);
    }

    void entreDataIntoDriverLicenseTable() {
        Driver driver = null;
        if (tableViewDriver.getSelectionModel().getSelectedItem() != null) {
            driver = driverList.stream().filter(searchDriver -> Objects.equals(searchDriver.getId(),
                            tableViewDriver.getSelectionModel().getSelectedItem().getValue().getId().getValue()))
                    .findFirst().orElseThrow(null);
        }
        final TreeItem<DriversLicenseView> driverLicenseTreeItem;
        if (driver != null) {
            driverLicenseTreeItem = new RecursiveTreeItem<>(
                    FXCollections.observableArrayList(
                            driverLicenseService.mapToDriverLicenseViewList(driver.getDriversLicense())
                    ),
                    RecursiveTreeObject::getChildren
            );
        } else {
            driverLicenseTreeItem = new TreeItem<>();
        }

        tableViewDriverLicense.setRoot(driverLicenseTreeItem);
        tableViewDriverLicense.setShowRoot(false);
    }


    @FXML
    void onSelectingLine() {
        isDisabledButton();
        createTableDriverLicense();
        onSelectingLineDriverLicense();
    }

    @FXML
    void onSelectingLineDriverLicense() {
        isDisabledButtonDriverLicense();
    }

    void isDisabledButton() {
        updateDrive.setDisable(tableViewDriver.getSelectionModel().getSelectedItem() == null);
        deleteDrive.setDisable(tableViewDriver.getSelectionModel().getSelectedItem() == null);
        addDriveLicense.setDisable(tableViewDriver.getSelectionModel().getSelectedItem() == null);
    }

    void isDisabledButtonDriverLicense() {
        deleteDriveLicense.setDisable(tableViewDriverLicense.getSelectionModel().getSelectedItem() == null);
        updateDriveLicense.setDisable(tableViewDriverLicense.getSelectionModel().getSelectedItem() == null);
    }
}
