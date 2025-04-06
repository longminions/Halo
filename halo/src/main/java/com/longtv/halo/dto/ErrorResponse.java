package com.longtv.halo.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
}
