package com.ghkwhd.shop.service.itemDTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemSaveDTO {

    private String itemName;
    private Integer price;
    private String seller;
    private String content;
    private MultipartFile thumbnailName;

}
