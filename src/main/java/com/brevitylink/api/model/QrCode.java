package com.brevitylink.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@Table(name = "qrcode")
public class QrCode {
    public QrCode(Link link) {
        this.link = link;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "link_id")
    private Link link;

    @Column(name = "count_click_qrcode")
    private Integer countClickQrcode;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusion = LocalDateTime.now();
}
