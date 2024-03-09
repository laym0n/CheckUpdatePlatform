CREATE TABLE IF NOT EXISTS WEBRESOURCE_OBSERVING
(
    id               UUID                     NOT NULL PRIMARY KEY,
    create_date      TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL,
    version          BIGINT                   NOT NULL,

    status           VARCHAR(50)              NOT NULL,
    need_notify      VARCHAR(50)              NOT NULL,
    user_id          UUID                     NOT NULL,
    webresource_id   UUID                     NOT NULL
);

ALTER TABLE WEBRESOURCE_OBSERVING
    ADD CONSTRAINT fk_webresource_observing_webresource_webresource_id
        FOREIGN KEY (webresource_id)
            REFERENCES WEBRESOURCE (id);

ALTER TABLE WEBRESOURCE_OBSERVING
    ADD CONSTRAINT fk_webresource_observing_user_user_id
        FOREIGN KEY (user_id)
            REFERENCES USERS (id);