package com.ghkwhd.shop.domain.item;

import lombok.Data;

import javax.persistence.*;

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
    private String thumbnailName;
    @Column(name = "thumbnail_UUID")
    private String thumbnailUUID;

    public Item() {
    }

    public Item(String itemName, Integer price, String seller, String content, String thumbnailName, String thumbnailUUID) {
        this.itemName = itemName;
        this.price = price;
        this.seller = seller;
        this.content = content;
        this.thumbnailName = thumbnailName;
        this.thumbnailUUID = thumbnailUUID;
    }
}
