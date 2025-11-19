package com.longtv.halo.dto;

public record ErrorResponse(
        String errorCode,
        String message,
        String path,
        String traceId
) {}
