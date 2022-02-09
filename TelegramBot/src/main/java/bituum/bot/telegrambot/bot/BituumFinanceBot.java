package bituum.bot.telegrambot.bot;

import bituum.bot.telegrambot.exception.CommandIsEmptyException;
import bituum.bot.telegrambot.exception.MessageIsEmptyException;
import bituum.bot.telegrambot.handler.CustomCallbackHandler;
import bituum.bot.telegrambot.handler.MessageHandler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class BituumFinanceBot extends TelegramLongPollingBot {
    private final CustomCallbackHandler callbackHandler = new CustomCallbackHandler();
    private final MessageHandler messageHandler = new MessageHandler();
    private final String botUsername = "@BituumFinance_bot";
    private final String botToken = "5274298440:AAFS96Hw2W5BR3HFh75JmxfcOPTyDvJQHd4";

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        //TODO HANDLERS  of message and callback(keyboard)
        //TODO make request to my service discovery like http://localhost:8000/tink/getCandels/SBER
        //TODO it should make a responce from one of the instances of tinkov service
        //also todo cbr service
        //TODO сделать подписку бота, добавление в фаварит бумаги, отслеживание цени и уведомление
        if(update.hasMessage()){
            log.info(update.getMessage().toString());
            try{
                MessageEntity commandEntity =
                        update.getMessage().getEntities().stream()
                                .filter(e -> "bot_command".equals(e.getType()))
                                .findFirst()
                                .orElseThrow(
                                        () -> new RuntimeException("Error during extracting Command Entity")
                                );
                List<List<InlineKeyboardButton>> keyboard = messageHandler.handle(update.getMessage());
                execute(
                        SendMessage.builder()
                                .text(update.getMessage()
                                        .getText()
                                        .substring(commandEntity.getLength())
                                        .toUpperCase())
                                .chatId(update.getMessage().getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup
                                        .builder()
                                        .keyboard(keyboard)
                                        .build())
                                .build());
            }catch (MessageIsEmptyException | CommandIsEmptyException emptyException){
                execute(
                        SendMessage
                                .builder()
                                .text("Я не понял, чего вы хотите... Введите команду /ticker 'название акции'")
                                .chatId(update
                                        .getMessage()
                                        .getChatId()
                                        .toString())

                                .build()
                );
            }
        } else if(update.hasCallbackQuery()){
            Message message = update.getCallbackQuery().getMessage();
            List<List<InlineKeyboardButton>> keyboard = callbackHandler.handle(update.getCallbackQuery());
            try{
                execute(
                        //TODO выдает ошибку о том, что нужно внести изменения в edit, иначе какой смысл изменять
                        EditMessageReplyMarkup
                                .builder()
                                .chatId(message.getChatId().toString())
                                .messageId(message.getMessageId())
                                .replyMarkup(InlineKeyboardMarkup
                                        .builder()
                                        .keyboard(keyboard)
                                        .build())
                                .build());
            }catch (NullPointerException | TelegramApiRequestException exception ){
                System.out.println("no edit blah blah blah");
            }
            execute(SendPhoto.builder()
                    .chatId(message.getChatId().toString())
                    .photo(new InputFile(new File("/home/bituum/IdeaProjects/BituumFinance/TelegramBot/src/main/resources/image.png")))
                    .build());
        }

    }
}
