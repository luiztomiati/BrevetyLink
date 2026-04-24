package com.brevitylink.api.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshToken(@NotBlank String refreshToken) {
}
