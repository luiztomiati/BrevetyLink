package com.brevitylink.api.repository;

import com.brevitylink.api.model.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

}
