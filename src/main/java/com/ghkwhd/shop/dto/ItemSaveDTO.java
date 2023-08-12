package com.ghkwhd.shop.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemSaveDTO {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min=1000, max=999999)
    private Integer price;

    @NotBlank
    private String seller;

    @NotBlank
    private String content;

    private MultipartFile thumbnailName;

    private Double avgStar = 0.0;

}
