package com.longtv.halo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Username hoặc email của người dùng", example = "abc or abc@mail.com")
    String username,


    @NotBlank
    @Size(min = 8, max = 100)
    @Schema(description = "Mật khẩu plaintext", example = "P@ssw0rd!", accessMode = Schema.AccessMode.WRITE_ONLY)
    String password
){}
