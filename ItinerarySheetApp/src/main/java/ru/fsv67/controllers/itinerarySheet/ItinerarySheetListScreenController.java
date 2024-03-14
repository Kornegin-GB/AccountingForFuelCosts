package ru.fsv67.controllers.itinerarySheet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
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
import ru.fsv67.models.itinerarySheet.ItinerarySheet;
import ru.fsv67.models.itinerarySheet.ItinerarySheetView;
import ru.fsv67.services.itinerarySheet.ItinerarySheetService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("/screens/itinerarySheet/itinerary-sheet-list-screen.fxml")
@EnableConfigurationProperties(AppProperties.class)
public class ItinerarySheetListScreenController implements Initializable {
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;
    private final ItinerarySheetService itinerarySheetService;

    List<ItinerarySheet> itinerarySheetList;

    @FXML
    private JFXButton exitButton;

    @FXML
    private JFXTreeTableView<ItinerarySheetView> tableViewItinerarySheet;

    @FXML
    private Label labelExitFuel;

    @FXML
    private Label labelExitOdometer;

    @FXML
    private Label labelReturnFuel;

    @FXML
    private Label labelReturnOdometer;

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

    @FXML
    void onSelectingLine() {
        if (tableViewItinerarySheet.getSelectionModel().getSelectedItem() != null) {
            ItinerarySheetView itinerarySheetView = tableViewItinerarySheet.getSelectionModel().getSelectedItem().getValue();
            labelExitOdometer.setText("Одометр при выезде: " + itinerarySheetView.getExitOdometer().getValue());
            labelReturnOdometer.setText("Одометр при возвращении: " + itinerarySheetView.getReturnOdometer().getValue());

            labelExitFuel.setText("Остаток топлива при выезде: " + itinerarySheetView.getInitialBalance().getValue());
            labelReturnFuel.setText("Остаток топлива при возвращении: " + itinerarySheetView.getFinalBalance().getValue());
        }
    }

    @FXML
    void onClick(MouseEvent event) {
        if (tableViewItinerarySheet.getSelectionModel().getSelectedItem() != null && event.getClickCount() == 2) {
            fxWeaver.loadController(ItinerarySheetUpdateScreenController.class).itinerarySheetView =
                    tableViewItinerarySheet.getSelectionModel().getSelectedItem().getValue();
            Stage stage = new Stage();
            Scene scene = new Scene(fxWeaver.loadView(ItinerarySheetUpdateScreenController.class));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setTitle(titleApp.getTitleApp());
            stage.showAndWait();
            enterDataItinerarySheetTable();
        }
    }

    @FXML
    void onClickDelete() {
        if (tableViewItinerarySheet.getSelectionModel().getSelectedItem() != null) {
            ItinerarySheetView value = tableViewItinerarySheet.getSelectionModel().getSelectedItem().getValue();
            itinerarySheetService.deleteItinerarySheet(value.getId().getValue());
            enterDataItinerarySheetTable();
        }
    }

    @FXML
    void onClickCreate() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(ItinerarySheetCreateScreenController.class));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.showAndWait();
        enterDataItinerarySheetTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createColumTable();
        enterDataItinerarySheetTable();
    }

    void createColumTable() {
        JFXTreeTableColumn<ItinerarySheetView, String> numberLine = new JFXTreeTableColumn<>("№ п.п.");
        numberLine.setMinWidth(-1);
        numberLine.setCellValueFactory(param -> param.getValue().getValue().getNumberLine().asString());
        numberLine.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<ItinerarySheetView, String> documentNumber = new JFXTreeTableColumn<>("Серия и номер");
        documentNumber.setMinWidth(-1);
        documentNumber.setCellValueFactory(param -> param.getValue().getValue().getDocumentNumber());
        documentNumber.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<ItinerarySheetView, String> departureDate = new JFXTreeTableColumn<>("Дата выезда");
        departureDate.setMinWidth(-1);
        departureDate.setCellValueFactory(param -> param.getValue().getValue().getDepartureDate());
        departureDate.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<ItinerarySheetView, String> returnDate = new JFXTreeTableColumn<>("Дата возвращения");
        returnDate.setMinWidth(-1);
        returnDate.setCellValueFactory(param -> param.getValue().getValue().getReturnDate());
        returnDate.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<ItinerarySheetView, String> car = new JFXTreeTableColumn<>("Автомобиль");
        car.setMinWidth(-1);
        car.setCellValueFactory(param -> param.getValue().getValue().getCar());
        car.setStyle("-fx-alignment: center;");

        JFXTreeTableColumn<ItinerarySheetView, String> driver = new JFXTreeTableColumn<>("Водитель");
        driver.setMinWidth(-1);
        driver.setCellValueFactory(param -> param.getValue().getValue().getDriver());
        driver.setStyle("-fx-alignment: center;");

        tableViewItinerarySheet.getColumns().setAll(
                List.of(numberLine, documentNumber, departureDate, returnDate, car, driver)
        );
    }

    private void enterDataItinerarySheetTable() {
        itinerarySheetList = itinerarySheetService.getItinerarySheetList();
        final TreeItem<ItinerarySheetView> itinerarySheetViewTreeItem =
                new RecursiveTreeItem<>(
                        FXCollections.observableArrayList(
                                itinerarySheetService.mapToItinerarySheetViewList(itinerarySheetList)
                        ),
                        RecursiveTreeObject::getChildren
                );
        tableViewItinerarySheet.setRoot(itinerarySheetViewTreeItem);
        tableViewItinerarySheet.setShowRoot(false);
    }
}
