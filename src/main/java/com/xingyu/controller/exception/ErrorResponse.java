package com.xingyu.controller.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ErrorResponse {
    private long timestamp;
    private int status;
    private String error;
}
