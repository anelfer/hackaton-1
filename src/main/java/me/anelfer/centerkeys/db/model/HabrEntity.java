package me.anelfer.centerkeys.db.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "habr")
public class HabrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    private String title;
    private String text;
    private String url;
    private String time;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public HabrEntity() {
    }

    public HabrEntity(String tag, String title, String text, String url, String time) {
        this.tag = tag;
        this.title = title;
        this.text = text;
        this.url = url;
        this.time = time;
    }

}
