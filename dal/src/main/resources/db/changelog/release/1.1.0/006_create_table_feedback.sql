CREATE TABLE IF NOT EXISTS FEEDBACK
(
    id               UUID                     NOT NULL PRIMARY KEY,
    create_date      TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL,
    version          BIGINT                   NOT NULL,

    comment          VARCHAR(200),
    rating           INTEGER                  NOT NULL,
    plugin_id        UUID                     NOT NULL,
    user_id          UUID                     NOT NULL
);

ALTER TABLE FEEDBACK
    ADD CONSTRAINT fk_feedback_plugin_plugin_id
        FOREIGN KEY (plugin_id)
            REFERENCES PLUGIN (id);

ALTER TABLE FEEDBACK
    ADD CONSTRAINT fk_user_plugin_user_id
        FOREIGN KEY (user_id)
            REFERENCES USERS (id);