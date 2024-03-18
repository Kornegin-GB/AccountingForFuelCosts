package ru.fsv67.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.fsv67.AppProperties;
import ru.fsv67.controllers.car.BrandListScreenController;
import ru.fsv67.controllers.car.CarListScreenController;
import ru.fsv67.controllers.car.ModelListScreenController;
import ru.fsv67.controllers.driver.DriverListScreenController;
import ru.fsv67.controllers.itinerarySheet.ItinerarySheetListScreenController;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/main-screen.fxml")
@RequiredArgsConstructor
@EnableConfigurationProperties(AppProperties.class)
public class MainScreenController implements Initializable {
    private final FxWeaver fxWeaver;
    private final AppProperties titleApp;

    @FXML
    private Button buttonExit;

    @FXML
    void brandFuel() {

    }

    @FXML
    void brandGuide() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(BrandListScreenController.class));
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.setResizable(false);
        stage.show();
        Stage windowStage = (Stage) buttonExit.getScene().getWindow();
        windowStage.close();
    }

    @FXML
    void carGuide() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(CarListScreenController.class));
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.show();
        Stage windowStage = (Stage) buttonExit.getScene().getWindow();
        windowStage.close();
    }

    @FXML
    void driverGuide() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(DriverListScreenController.class));
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.show();
        Stage windowStage = (Stage) buttonExit.getScene().getWindow();
        windowStage.close();
    }

    @FXML
    void itinerarySheets() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(ItinerarySheetListScreenController.class));
        stage.setScene(scene);
        stage.setTitle(titleApp.getTitleApp());
        stage.show();
        Stage windowStage = (Stage) buttonExit.getScene().getWindow();
        windowStage.close();
    }

    @FXML
    void exitApp() {
        Stage windowStage = (Stage) buttonExit.getScene().getWindow();
        windowStage.close();
        Platform.exit();
    }

    @FXML
    void modelGuide() {
        Stage stage = new Stage();
        Scene scene = new Scene(fxWeaver.loadView(ModelListScreenController.class));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(titleApp.getTitleApp());
        stage.show();
        Stage windowStage = (Stage) buttonExit.getScene().getWindow();
        windowStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
