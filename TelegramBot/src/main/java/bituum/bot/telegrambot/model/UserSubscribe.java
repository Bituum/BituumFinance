package bituum.bot.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_sub", schema = "bot_schema")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscribe {
    @Id
    private int id;
    @Column(name = "ticker_name")
    private String ticker;
    @Column(name = "goal")
    private double goal;
    @Column(name = "chat_id")
    private String chatId;
}
