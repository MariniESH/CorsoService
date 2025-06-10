package com.example.corso.entity.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiBaseResponse<T> {
    private T content;
    private Integer code;
    private String message;
}
