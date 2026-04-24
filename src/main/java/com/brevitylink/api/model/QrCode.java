package com.brevitylink.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
public class QrCode {
    public QrCode(Link link ) {
       this.Link = link;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "link_id")
    private Link Link;

    @Column(name = "count_click_qrcode")
    private Integer countClickQrcode;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusion = LocalDateTime.now();
}
