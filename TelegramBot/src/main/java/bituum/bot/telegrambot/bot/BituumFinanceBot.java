package bituum.bot.telegrambot.bot;

import bituum.bot.telegrambot.exception.CommandIsEmptyException;
import bituum.bot.telegrambot.exception.MessageIsEmptyException;
import bituum.bot.telegrambot.handler.CustomCallbackHandler;
import bituum.bot.telegrambot.handler.MessageHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
@Slf4j
//@PropertySource("classpath:/telegramBotConfig.properties")
public class BituumFinanceBot extends TelegramLongPollingBot {
    private CustomCallbackHandler callbackHandler = new CustomCallbackHandler();
    private MessageHandler messageHandler = new MessageHandler();

    @Override
    public String getBotUsername() {
        return "@BituumFinance_bot";
    }

    @Override
    public String getBotToken() {
        return "5165061899:AAH8IUwS09FjRO-d1BJkP1p_cMqMiEc79No";
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
                List<List<InlineKeyboardButton>> keyboard = messageHandler.handle(update.getMessage());
                execute(
                        SendMessage.builder()
                                .text("Привет!")
                                .chatId(update.getMessage().getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboard).build())
                                .build());
            }catch (MessageIsEmptyException | CommandIsEmptyException emptyException){
                execute(
                        SendMessage.builder()
                                .text("Я не понял, чего вы хотите... Введите команду /ticker 'название акции'")
                                .chatId(update.getMessage().getChatId().toString())
                                .build()
                );
            }
        } else if(update.hasCallbackQuery()){
            Message message = update.getCallbackQuery().getMessage();
            List<List<InlineKeyboardButton>> keyboard = callbackHandler.handle(update.getCallbackQuery());
            execute(
                    //TODO выдает ошибку о том, что нужно внести изменения в edit, иначе какой смысл изменять
                    EditMessageReplyMarkup.builder()
                            .chatId(message.getChatId().toString())
                            .messageId(message.getMessageId())
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboard).build())
                            .build());
        }

    }
}
