CREATE TABLE IF NOT EXISTS NOTIFICATION
(
    id               UUID                     NOT NULL PRIMARY KEY,
    create_date      TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL,
    version          BIGINT                   NOT NULL,

    message          VARCHAR(50)              NOT NULL,
    user_id          UUID                     NOT NULL
);

ALTER TABLE NOTIFICATION
    ADD CONSTRAINT fk_notification_user_user_id
        FOREIGN KEY (user_id)
            REFERENCES USERS (id);