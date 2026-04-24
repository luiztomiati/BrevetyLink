package com.brevitylink.api.dto;

public record LinkResponse(

        String urlOriginal,
        String shortCode,
        String newUrl) {
}
