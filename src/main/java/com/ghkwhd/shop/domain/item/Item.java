package com.ghkwhd.shop.domain.item;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;
    private String seller;
    private String content;

    public Item() {
    }

    public Item(String itemName, Integer price, String seller, String content) {
        this.itemName = itemName;
        this.price = price;
        this.seller = seller;
        this.content = content;
    }
}
