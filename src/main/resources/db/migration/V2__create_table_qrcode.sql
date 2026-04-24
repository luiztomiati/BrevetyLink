CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS QRCODE (
                                      id UUID NOT NULL,
                                      link_id BIGINT,
                                      count_click_qrcode INTEGER DEFAULT 0,
                                      data_inclusao TIMESTAMP WITHOUT TIME ZONE NOT NULL,

                                      CONSTRAINT pk_qrcode PRIMARY KEY (id),

    CONSTRAINT fk_qrcode_link FOREIGN KEY (link_id) REFERENCES LINKS (id)
    ON DELETE CASCADE,
    CONSTRAINT uk_qrcode_link UNIQUE (link_id)
    );

CREATE INDEX idx_qrcode_link_id ON QRCODE(link_id);