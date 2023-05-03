package com.ghkwhd.shop.controller.itemDTO;

import lombok.Data;

@Data
public class ItemUpdateDTO {

    private Long id;
    private String itemName;
    private Integer price;
    private String seller;
    private String content;
}
