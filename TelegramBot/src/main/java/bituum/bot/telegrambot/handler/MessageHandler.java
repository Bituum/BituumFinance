package bituum.bot.telegrambot.handler;

import bituum.bot.telegrambot.exception.CommandIsEmptyException;
import bituum.bot.telegrambot.exception.MessageIsEmptyException;
import bituum.bot.telegrambot.exception.SubscribeException;
import bituum.bot.telegrambot.model.UserSubscribe;
import bituum.bot.telegrambot.service.SubscribeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MessageHandler {
    @Autowired
    private SubscribeService subscribeService;
    @SneakyThrows
    public List<List<InlineKeyboardButton>> handle(Message message){
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream()
                            .filter(e -> "bot_command".equals(e.getType()))
                            .findFirst();

            if (commandEntity.isPresent()) {
                String command =
                        message.getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/ticker":
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


                    case "/subscribe":
                        String[] list = message.getText().split(" ");
                        String ticker = list[1];
                        double goal = Double.parseDouble(list[2]);
                        List<List<InlineKeyboardButton>> subButtons = new ArrayList<>();
                            subscribeService.subscribe(UserSubscribe.builder()
                                    .chatId(message.getChatId().toString())
                                    .goal(goal)
                                    .ticker(ticker)
                                    .build());

                        subButtons.add(
                                Arrays.asList(
                                        InlineKeyboardButton.builder()
                                                .text("отписаться")
                                                .callbackData("graph")
                                                .build()
                                )
                        );
                        return subButtons;
                    case "/unsubscribe":
                        String[] splitList = message.getText().split(" ");
                        String unsubTicker = splitList[1];
                        List<List<InlineKeyboardButton>> unsubButton = new ArrayList<>();
                        subscribeService.subscribe(UserSubscribe.builder()
                                .chatId(message.getChatId().toString())
                                .ticker(unsubTicker)
                                .build());

                        unsubButton.add(
                                Arrays.asList(
                                        InlineKeyboardButton.builder()
                                                .text("отписаться")
                                                .callbackData("graph")
                                                .build()
                                )
                        );
                        return unsubButton;
                    default:
                        throw new CommandIsEmptyException("command is empty or wrong");
                }

            }
        }
        throw new MessageIsEmptyException("message is empty");
    }
}
