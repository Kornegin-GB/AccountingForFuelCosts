package ru.fsv67.app;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.fsv67.AppProperties;
import ru.fsv67.controllers.MainScreenController;

@Component
@EnableConfigurationProperties(AppProperties.class)
@RequiredArgsConstructor
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final AppProperties titleApp;
    private final FxWeaver fxWeaver;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        Scene scene = new Scene(fxWeaver.loadView(MainScreenController.class));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(titleApp.getTitleApp());
        stage.show();
    }
}
