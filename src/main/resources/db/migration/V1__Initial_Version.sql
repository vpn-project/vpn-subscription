CREATE TABLE IF NOT EXISTS "user"
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE  NOT NULL,
    password VARCHAR(60)         NOT NULL,
    email    VARCHAR(256) UNIQUE NOT NULL,
    role     VARCHAR(16) DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS account
(
    id              INTEGER PRIMARY KEY,
    username        VARCHAR(255),
    is_main_account BOOLEAN
);

CREATE TABLE IF NOT EXISTS account_linked_account
(
    id                INTEGER PRIMARY KEY,
    main_account_id   INTEGER,
    linked_account_id INTEGER,
    FOREIGN KEY (main_account_id) REFERENCES account (id),
    FOREIGN KEY (linked_account_id) REFERENCES account (id)
);

CREATE TABLE IF NOT EXISTS token
(
    id    serial PRIMARY KEY,
    token VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS subscription
(
    id              serial PRIMARY KEY,
    expiration_date TIMESTAMP,
    is_active       BOOLEAN,
    token_id        INTEGER,
    FOREIGN KEY (token_id) REFERENCES token (id)
);
