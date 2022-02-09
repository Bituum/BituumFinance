package bituum.bot.telegrambot.handler;


import bituum.bot.telegrambot.service.ProcessService;
import bituum.bot.telegrambot.service.ProcessServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
public class CustomCallbackHandler {
    private ProcessService processService = new ProcessServiceImpl();

    private final String location = "/home/bituum/IdeaProjects/BituumFinance/TelegramBot/src/main/resources/image.png";

    @SneakyThrows
    public List<List<InlineKeyboardButton>> handle(CallbackQuery callbackQuery){
        String action = callbackQuery.getData();
        switch (action){
            case "graph":
                log.info(callbackQuery.getMessage().getText().toString());
                log.info("graph service");
                processService.execute(location, callbackQuery.getMessage().getText());
                break;
            case  "last_price":
                log.info("price service");
                break;
            case  "subscribe":
                log.info("sub service");
                break;
        }

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(
                Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("График")
                        .callbackData("graph")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Цена")
                        .callbackData("last_price")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Подписка")
                        .callbackData("subscribe")
                        .build()
                )
        );
        return buttons;
    }
}
