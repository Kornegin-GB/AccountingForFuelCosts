package ru.fsv67;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import ru.fsv67.app.StageReadyEvent;

public class ItinerarySheetJavaFxApp extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void init() {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                applicationContext -> {
                    applicationContext.registerBean(Application.class, () -> ItinerarySheetJavaFxApp.this);
                };
        applicationContext = new SpringApplicationBuilder()
                .sources(ItinerarySheetApp.class)
                .initializers(initializer)
                .run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }
}
