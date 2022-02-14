package bituum.bot.telegrambot.service.impl;

import bituum.bot.telegrambot.exception.SubscribeException;
import bituum.bot.telegrambot.exception.SubscribeException;
import bituum.bot.telegrambot.model.UserSubscribe;
import bituum.bot.telegrambot.repository.UserSubscribeRepository;
import bituum.bot.telegrambot.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private UserSubscribeRepository repository;

    @Override
    public void subscribe(UserSubscribe subscribe) {
        repository.save(subscribe);
        if(repository.findById(subscribe.getId()).isEmpty()){
            throw new SubscribeException("Error while adding subscribe");
        }
    }

    @Override
    public void unsubscribe(UserSubscribe subscribe) {
        repository.deleteUserTickerPriceByChatIdAndTicker(subscribe.getTicker(), subscribe.getChatId());
        if(repository.findById(subscribe.getId()).isPresent()){
            throw new SubscribeException("Error while deleting subscribe");
        }
    }

    @Override
    public boolean isSubscribeOn(String chatId, String ticker) {
        return repository.findByChatIdAndTicker(chatId, ticker);
    }
}
