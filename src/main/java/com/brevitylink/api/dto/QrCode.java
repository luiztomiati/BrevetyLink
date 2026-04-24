package com.brevitylink.api.dto;

import jakarta.validation.constraints.NotBlank;

public record QrCode(
        @NotBlank(message = "Url original não pode estar vazia")
        @org.hibernate.validator.constraints
                .URL(message = "Informe uma URL completa (ex: https://google.com)")
        String url) {
}
