package com.ghkwhd.shop.service.itemDTO;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateDTO {

    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min=0, max=999999)
    private Integer price;

    @NotBlank
    private String seller;

    @NotBlank
    private String content;

    private String thumbnailName;
    private String thumbnailUUID;

    private Double avgStar;
}
