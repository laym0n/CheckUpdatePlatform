CREATE TABLE IF NOT EXISTS USERS
(
    id               UUID                     NOT NULL PRIMARY KEY,
    create_date      TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL,
    version          BIGINT                   NOT NULL,

    email            VARCHAR(50)              NOT NULL,
    telegram_info    VARCHAR(50),
    password         VARCHAR(500)             NOT NULL,
    enabled          BOOLEAN                  NOT NULL,
    roles            VARCHAR(50)              NOT NULL
);

CREATE UNIQUE INDEX idx_user_email ON USERS (email);