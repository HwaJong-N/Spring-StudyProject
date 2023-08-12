package com.ghkwhd.shop.dto;

import lombok.Data;

@Data
public class ReviewSaveDTO {

    private Long itemId;
    private Integer star;
    private String nickname;
    private String comment;
}
