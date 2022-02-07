package dev.bituum.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Quotes {
    @Id
    private String id;
    private int NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private Double Value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quotes quotes = (Quotes) o;
        return id != null && Objects.equals(id, quotes.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
