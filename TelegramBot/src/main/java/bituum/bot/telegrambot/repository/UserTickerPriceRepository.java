package bituum.bot.telegrambot.repository;

import bituum.bot.telegrambot.model.UserTickerPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTickerPriceRepository extends JpaRepository<UserTickerPrice, Integer> {
    Optional<UserTickerPrice> getUserTickerPriceByChatIdAndName(String chatId, String name);
}
