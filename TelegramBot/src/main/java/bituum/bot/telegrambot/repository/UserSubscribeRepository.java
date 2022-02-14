package bituum.bot.telegrambot.repository;

import bituum.bot.telegrambot.model.UserSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubscribeRepository extends JpaRepository<UserSubscribe, Integer> {
    boolean findByChatIdAndTicker(String chatId, String ticker);
    void deleteUserTickerPriceByChatIdAndTicker(String chatId, String name);
}
