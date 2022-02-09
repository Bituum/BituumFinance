package bituum.bot.telegrambot.service;

import java.io.IOException;

public interface ProcessService {
    public void execute(String location, String ticker) throws IOException, InterruptedException;
}
