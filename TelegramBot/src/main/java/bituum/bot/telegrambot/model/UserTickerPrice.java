package bituum.bot.telegrambot.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "", schema = "")
public class UserTickerPrice {
    @Id
    int id;
    private String name;
    private double price;
}
