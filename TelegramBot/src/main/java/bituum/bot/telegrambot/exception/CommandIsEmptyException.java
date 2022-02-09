package bituum.bot.telegrambot.exception;

public class CommandIsEmptyException extends RuntimeException{
    public CommandIsEmptyException(String message) {
        super(message);
    }
}
