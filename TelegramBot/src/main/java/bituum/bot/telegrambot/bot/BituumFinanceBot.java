package bituum.bot.telegrambot.bot;

import bituum.bot.telegrambot.handler.CustomCallbackHandler;
import bituum.bot.telegrambot.handler.MessageHandler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
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

@Slf4j
@Component
@PropertySource("classpath:/telegramBotConfig.properties")
public class BituumFinanceBot extends TelegramLongPollingBot {
    @Autowired
    private CustomCallbackHandler callbackHandler;
    private final MessageHandler messageHandler = new MessageHandler();
    @Value("${telegram.botname}")
    private String botUsername;
    @Value("${telegram.token}")
    private String botToken;

    private boolean hasError = false;

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
            }catch (RuntimeException emptyException){
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
            try{
                List<List<InlineKeyboardButton>> keyboard = callbackHandler.handle(update.getCallbackQuery());
                execute(
                        //выдает ошибку о том, что нужно внести изменения в edit, иначе какой смысл изменять
                        EditMessageReplyMarkup
                                .builder()
                                .chatId(message.getChatId().toString())
                                .messageId(message.getMessageId())
                                .replyMarkup(InlineKeyboardMarkup
                                        .builder()
                                        .keyboard(keyboard)
                                        .build())
                                .build());
            }catch (TelegramApiRequestException exception ){
                log.info("edit error captured ^_^");
            }catch (RuntimeException exception){
                exception.printStackTrace();
                execute(SendMessage
                        .builder()
                        .chatId(message.getChatId().toString())
                        .text("Я не нашел такой ticker! Попробуйте еще раз /ticker 'Название бумаги'")
                        .build()
                );
                hasError = true;
            }
            //К сожалению надо использовать систему с глобальным датчик ошибки
            if(CustomCallbackHandler.information != null && !hasError && !CustomCallbackHandler.amIStopLooking){
                execute(SendMessage
                        .builder()
                        .chatId(message.getChatId().toString())
                        .text(CustomCallbackHandler.information)
                        .build());
                CustomCallbackHandler.information = null;
                hasError = false;

            } else if(!hasError && !CustomCallbackHandler.amIStopLooking) {
                execute(SendPhoto.builder()
                        .chatId(message.getChatId().toString())
                        .photo(new InputFile(new File("/home/bituum/IdeaProjects/BituumFinance/TelegramBot/src/main/resources/image.png")))
                        .build()
                );
                hasError = false;
            }
            //bruh
            CustomCallbackHandler.amIStopLooking = false;

        }

    }
}
