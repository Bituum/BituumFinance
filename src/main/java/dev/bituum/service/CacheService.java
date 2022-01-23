package dev.bituum.service;

import dev.bituum.model.Quotes;

import java.util.Date;
import java.util.List;

public interface CacheService {
    void addCache(String cacheString, String cachedDay);
    boolean checkCache(String thisDay);
    String getCachedQuotes();
    String getCachedDay();
}
