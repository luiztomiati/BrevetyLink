CREATE TABLE USERS (
                       id UUID NOT NULL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       nick_name VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       data_inclusion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE links
    ADD COLUMN user_id UUID;

ALTER TABLE links
    ADD CONSTRAINT fk_links_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE;