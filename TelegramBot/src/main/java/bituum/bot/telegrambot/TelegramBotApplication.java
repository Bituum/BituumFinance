package bituum.bot.telegrambot;

import bituum.bot.telegrambot.bot.InitializeBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) {
        InitializeBot.init();
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}