package dev.bituum.tinkoffmservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tickers", schema = "schema_tinkoff")
public class Ticker {
    @Id
    int id;
    @Column(name = "name")
    String name;
    @Column(name = "figi")
    String figi;
    @Column(name = "ticker_name")
    String tickerName;
}
