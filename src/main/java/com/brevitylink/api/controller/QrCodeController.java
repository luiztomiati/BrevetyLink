package com.brevitylink.api.controller;

import com.brevitylink.api.model.Users;
import com.brevitylink.api.service.QrCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@Tag(name = "QR Codes", description = "Geração de QR Codes para links")
@RestController
@RequestMapping("/qrcodes")

public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }
    @Operation(
            summary = "Gerar QR Code",
            description = "Gera um QR Code em formato PNG a partir de uma URL"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR Code gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "URL inválida"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(
            @Parameter(description = "URL para gerar o QR Code", example = "https://google.com")
            @RequestParam("url") String url,
            @AuthenticationPrincipal Users user) {

        byte[] qrGenerated = qrCodeService.executeGeneratedQrCode(url, user);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrGenerated);
    }
}
