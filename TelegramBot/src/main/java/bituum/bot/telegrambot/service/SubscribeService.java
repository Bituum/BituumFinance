package bituum.bot.telegrambot.service;

import bituum.bot.telegrambot.model.UserSubscribe;

public interface SubscribeService {
    void subscribe(UserSubscribe subscribe);
    void unsubscribe(UserSubscribe subscribe);
    boolean isSubscribeOn(String chatId, String ticker);
}
