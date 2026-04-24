package com.brevitylink.api.controller;

import com.brevitylink.api.model.Users;
import com.brevitylink.api.service.QrCodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qrcodes")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCode(@RequestParam("url") String url, @AuthenticationPrincipal Users user) {
        byte[] qrGenerated = qrCodeService.executeGeneratedQrCode(url, user);
        return ResponseEntity.ok(qrGenerated);
    }
}
