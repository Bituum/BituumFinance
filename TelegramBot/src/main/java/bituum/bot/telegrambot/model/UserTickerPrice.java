package bituum.bot.telegrambot.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_ticker_price", schema = "bot_schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTickerPrice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;
    @Column(name = "chat_id")
    private String chatId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
}
