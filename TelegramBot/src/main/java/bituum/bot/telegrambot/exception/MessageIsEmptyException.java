package bituum.bot.telegrambot.exception;

public class MessageIsEmptyException extends RuntimeException{
    public MessageIsEmptyException(String message) {
        super(message);
    }
}
