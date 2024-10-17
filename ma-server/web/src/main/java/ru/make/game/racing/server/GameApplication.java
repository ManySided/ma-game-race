package ru.make.game.racing.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.make.game.racing.server.networking.WebSocketServer;

@Slf4j
@SpringBootApplication
public class GameApplication implements CommandLineRunner {
    private final WebSocketServer webSocketServer;

    public GameApplication(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    public static void main(String[] args) {
        log.info("Старт сервиса");
        SpringApplication.run(GameApplication.class, args);
    }

    @Override
    public void run(String... strings) throws InterruptedException {
        webSocketServer.start();
    }
}