package dev.bituum.tinkoffmservice.service.impl;

import dev.bituum.tinkoffmservice.repository.TickerRepository;
import dev.bituum.tinkoffmservice.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TickerServiceImpl implements TickerService {
    @Autowired
    private TickerRepository repository;

    @Override
    public String findFigi(String name) throws IllegalArgumentException{
        String figi = repository
                .findTickerByName(name)
                .getFigi();

        if(figi == null){
            throw new IllegalArgumentException("wrong ticker or figi!");
        }
        return figi;
    }
}
