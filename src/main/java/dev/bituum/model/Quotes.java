package dev.bituum.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Quotes {
    @Id
    private String id;
    private int NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private Double Value;
}
