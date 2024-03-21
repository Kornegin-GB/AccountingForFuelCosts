package ru.fsv67.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/screens/error-screen.fxml")
@RequiredArgsConstructor
public class ErrorScreenController implements Initializable {
    public String message;
    @FXML
    private Label messageLabel;

    @FXML
    void onClickClose() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText(message);
    }
}
