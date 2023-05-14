package com.ghkwhd.shop.service.reviewDTO;

import lombok.Data;

@Data
public class ReviewSaveDTO {

    private Long itemId;
    private Integer star;
    private String nickname;
    private String comment;
}
