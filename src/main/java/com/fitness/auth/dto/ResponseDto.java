package com.fitness.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDto<T> {
    private Boolean status;
    private String message;
    private T data;

}
