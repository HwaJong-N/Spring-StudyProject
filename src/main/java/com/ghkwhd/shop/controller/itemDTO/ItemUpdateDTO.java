package com.ghkwhd.shop.controller.itemDTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemUpdateDTO {

    private Long id;
    private String itemName;
    private Integer price;
    private String seller;
    private String content;
    private String thumbnailName;
    private String thumbnailUUID;

}
