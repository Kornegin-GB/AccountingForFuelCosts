package ru.fsv67;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.app")
public class AppProperties {
    /**
     * Название окна программы
     */
    private String titleApp = "Demo app";
}
