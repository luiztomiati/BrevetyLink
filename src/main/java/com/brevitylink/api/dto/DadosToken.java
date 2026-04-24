package com.brevitylink.api.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosToken(@NotBlank String tokenAccess, @NotBlank String refreshToken) {
}
