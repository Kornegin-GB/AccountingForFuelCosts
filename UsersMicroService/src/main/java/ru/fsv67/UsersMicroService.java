package ru.fsv67;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Микро сервис управления пользователями системы
 */
@SpringBootApplication
public class UsersMicroService {
    public static void main(String[] args) {
        SpringApplication.run(UsersMicroService.class,args);
    }
}