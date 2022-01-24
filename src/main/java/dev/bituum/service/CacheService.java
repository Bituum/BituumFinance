package dev.bituum.service;

public interface CacheService {
    void addCache(String cacheString, String cachedDay);
    boolean checkCache(String thisDay);
    String getCachedQuotes();
    String getCachedDay();
}
