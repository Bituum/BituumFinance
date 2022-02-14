package bituum.bot.telegrambot.handler;

import bituum.bot.telegrambot.service.PriceService;

import bituum.bot.telegrambot.service.ProcessService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
@Service
public class CustomCallbackHandler {
    @Autowired
    private ProcessService processService;
    @Autowired
    private PriceService priceService;

    public static String information;
    public static boolean amIStopLooking = false;


    private final String location = "/home/bituum/IdeaProjects/BituumFinance/TelegramBot/src/main/resources/image.png";

    @SneakyThrows
    public List<List<InlineKeyboardButton>> handle(CallbackQuery callbackQuery){
        String action = callbackQuery.getData();
        switch (action) {
            case "graph" -> {
                log.info("graph service");
                processService.execute(location, callbackQuery.getMessage().getText());
                return initButtons();
            }
            case "last_price" -> {
                log.info("price service");
                information = priceService.getInformationAboutLastPrice(callbackQuery.getMessage().getChatId().toString(), callbackQuery.getMessage().getText());
                return initPriceServiceButtons();
            }
            case "subscribe" -> log.info("sub service");

            case "stop_looking_for" ->{
                log.info("stop looking for action");
                priceService.disableGetInformationAboutLastPrice(
                        callbackQuery.getMessage().getChatId().toString(),
                        callbackQuery.getMessage().getText());
                amIStopLooking = true;
                return initButtons();
            }

            case "get_info_about_ticker" ->{
                log.info("get_info_about_ticker_action");
                information = priceService.getInformationAboutLastPrice(callbackQuery.getMessage().getChatId().toString(), callbackQuery.getMessage().getText());
                return initPriceServiceButtons();
            }


        }

        return initButtons();
    }

    private List<List<InlineKeyboardButton>> initButtons(){
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("График")
                                .callbackData("graph")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text("Отслеживать цену")
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
    private List<List<InlineKeyboardButton>> initPriceServiceButtons(){
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("График")
                                .callbackData("graph")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text("прекратить отслеживать")
                                .callbackData("stop_looking_for")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text("узнать изменение")
                                .callbackData("get_info_about_ticker")
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


