package com.brevitylink.api.service;

import com.brevitylink.api.infraestructure.QrCodeInfraestructure;
import com.brevitylink.api.model.Link;
import com.brevitylink.api.model.QrCode;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.repository.QrCodeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class QrCodeService {

    @Value("${APP_URL}")
    String baseUrl;

    private final QrCodeRepository qrCodeRepository;
    private final QrCodeInfraestructure qrCodeInfraestructure;
    private final LinkService  linkService;

    public QrCodeService(QrCodeRepository qrCodeRepository, QrCodeInfraestructure qrCodeInfraestructure, LinkService linkService) {
        this.qrCodeRepository = qrCodeRepository;
        this.qrCodeInfraestructure = qrCodeInfraestructure;
        this.linkService = linkService;
    }

    public byte[] executeGeneratedQrCode(String url, Users user) {
        Link link = linkService.createdLink(url, user);
        QrCode qrCode = new QrCode(link);
        qrCodeRepository.save(qrCode);
        String newString = baseUrl + link.getShortCode();
        return qrCodeInfraestructure.generate(newString,250, 250);
    }
}