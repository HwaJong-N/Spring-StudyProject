package com.ghkwhd.shop.domain.review;

import com.ghkwhd.shop.domain.item.Item;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer star;
    private String nickname;
    private String comment;


    public Review() {
    }

    public Review(Item item, Integer star, String nickname, String comment) {
        this.item = item;
        this.star = star;
        this.nickname = nickname;
        this.comment = comment;
    }
}
