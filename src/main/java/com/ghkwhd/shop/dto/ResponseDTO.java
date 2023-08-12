package com.ghkwhd.shop.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO<T>{
    private T data;
    private HttpStatus status;

    public ResponseDTO(T data, HttpStatus httpStatus) {
        this.data = data;
        this.status = httpStatus;
    }
}
