package com.brevitylink.api.dto;
import com.brevitylink.api.model.Link;


public record LinkResponse(

        String urlOriginal,
        String shortCode,
        String newUrl) {

    public static LinkResponse fromEntity(Link link, String baseUrl) {
        return new LinkResponse(
                link.getLinkOriginal(),
                link.getShortCode(),
                baseUrl + "/" + link.getShortCode()
        );
    }
}
