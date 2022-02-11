package bituum.bot.telegrambot.service;

public interface PriceService {
    String getInformationAboutLastPrice(String chatId, String ticker);
    void disableGetInformationAboutLastPrice(String chatId, String ticker);
}
