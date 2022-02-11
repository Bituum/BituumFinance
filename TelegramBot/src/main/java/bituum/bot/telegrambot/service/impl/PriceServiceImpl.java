package bituum.bot.telegrambot.service.impl;

import bituum.bot.telegrambot.model.UserTickerPrice;
import bituum.bot.telegrambot.repository.UserTickerPriceRepository;
import bituum.bot.telegrambot.service.PriceService;
import bituum.bot.telegrambot.util.GetRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {
    @Autowired
    private UserTickerPriceRepository repository;
    //todo value annotation
    private final String endPoint = "http://localhost:8081/get_last_price/";

    @SneakyThrows
    @Override
    public String getInformationAboutLastPrice(String chatId, String ticker) {
        if(chatId == null || ticker == null){
            throw new NullPointerException("chatId or ticker is null. Please provide correct one");
        }else {
            double getPrice = Double.parseDouble(
                    GetRequest.doGet(endPoint + ticker)
            );
            Optional<UserTickerPrice> utp = repository.getUserTickerPriceByChatIdAndName(chatId, ticker);
            if(utp.isPresent()){
                double percent = calculatePercent(utp.get().getPrice(), getPrice);
                return ticker + ": " + getPrice + " | " + (percent > 0 ? "+"+ String.format("%,.3f", percent)
                        + "% "+ " \uD83D\uDCC8" : String.format("%.3f", percent) + "% "+ " \uD83D\uDCC9");
            }else {
                repository.save(UserTickerPrice.builder()
                        .chatId(chatId)
                        .price(getPrice)
                        .name(ticker)
                        .build()
                );
                return "цена " + ticker + " = " + getPrice;
            }

        }
    }

    public void disableGetInformationAboutLastPrice(String chatId, String ticker){
        repository.deleteById(findEntityId(chatId, ticker).getId());
    }

    private UserTickerPrice findEntityId(String chatId, String ticker){
        return repository.getUserTickerPriceByChatIdAndName(chatId, ticker)
                .orElseThrow(
                () -> new RuntimeException("There are not entity in the database!")
        );
    }


    private double calculatePercent(double oldPrice, double newPrice){
        System.out.println(((newPrice - oldPrice) / oldPrice) * 100);
        return ((newPrice - oldPrice) / oldPrice) * 100;
    }
}
