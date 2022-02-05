package dev.bituum.tinkoffmservice.repository;

import dev.bituum.tinkoffmservice.model.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<Ticker, Integer> {
    Ticker findTickerByTickerName(String name);
}
