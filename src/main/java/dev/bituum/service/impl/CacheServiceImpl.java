package dev.bituum.service.impl;

import dev.bituum.model.Quotes;
import dev.bituum.service.CacheService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CacheServiceImpl implements CacheService {
    private String cachedString;
    private String cachedDay;

    @Override
    public void addCache(String cachedString, String cachedDay) {
        this.cachedDay = cachedDay;
        this.cachedString = cachedString;
    }

    @Override
    public boolean checkCache(String thisDay) {
        if(cachedDay == null){
            return false;
        }
        return cachedDay.equals(thisDay);
    }

    @Override
    public String getCachedQuotes() {
        return this.cachedString;
    }
    @Override
    public String getCachedDay() {
        return cachedDay;
    }
}
