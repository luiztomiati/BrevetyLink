package com.brevitylink.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Link {

    public Link(String url) {
        this.linkOriginal = url;
    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "original_url")
    private String linkOriginal;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "click_count")
    private Long clickCount = 0L;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_inclusion")
    private LocalDateTime dataInclusion = LocalDateTime.now();

    @OneToOne(mappedBy = "Link", cascade = CascadeType.ALL, orphanRemoval = true)
    private QrCode qrCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}