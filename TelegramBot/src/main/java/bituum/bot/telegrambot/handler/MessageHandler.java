package bituum.bot.telegrambot.handler;

import bituum.bot.telegrambot.exception.CommandIsEmptyException;
import bituum.bot.telegrambot.exception.MessageIsEmptyException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MessageHandler {
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
                    default:
                        throw new CommandIsEmptyException("command is empty or wrong");
                }

            }
        }
        throw new MessageIsEmptyException("message is empty");
    }
}
