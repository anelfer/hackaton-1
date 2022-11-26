package me.anelfer.centerkeys.db.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "headhunter")
public class HeadHunterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    private String name;
    private int price;
    private String currency;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public HeadHunterEntity() {
    }

    public HeadHunterEntity(String tag, String name, int price, String currency) {
        this.tag = tag;
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

}
