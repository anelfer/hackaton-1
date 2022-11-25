package me.anelfer.centerkeys.db.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "stackoverflow")
public class StackoverflowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    private int total;
    private int today;
    private int week;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public StackoverflowEntity() {}

    public StackoverflowEntity(String tag, int total, int week,  int today) {
        this.tag = tag;
        this.total = total;
        this.week = week;
        this.today = today;
    }

}
