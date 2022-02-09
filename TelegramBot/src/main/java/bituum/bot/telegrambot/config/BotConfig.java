package bituum.bot.telegrambot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@PropertySource("classpath:/telegramBotConfig.properties")
public class BotConfig {
    @Value("${telegram.bot-name}")
    String userName;
    @Value("${telegram.token}")
    String botToken;
}