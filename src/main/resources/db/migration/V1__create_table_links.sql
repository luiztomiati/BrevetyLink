CREATE TABLE links (
                       id BIGSERIAL PRIMARY KEY,
                       original_url TEXT NOT NULL,
                       short_code VARCHAR(20) UNIQUE,
                       click_count INTEGER DEFAULT 0,
                       data_inclusion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);