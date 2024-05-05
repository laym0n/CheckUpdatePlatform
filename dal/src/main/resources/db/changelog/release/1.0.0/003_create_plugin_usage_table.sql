CREATE TABLE IF NOT EXISTS PLUGIN_USAGE
(
    id                    UUID                     NOT NULL PRIMARY KEY,
    create_date           TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date      TIMESTAMP WITH TIME ZONE NOT NULL,
    version               BIGINT                   NOT NULL,

    expired_date          TIMESTAMP WITH TIME ZONE,
    distribution_type     VARCHAR(50)              NOT NULL,
    distribution_duration BIGINT,
    distribution_cost NUMERIC(20, 2),
    user_id               UUID                     NOT NULL,
    plugin_id             UUID                     NOT NULL
);

ALTER TABLE PLUGIN_USAGE
    ADD CONSTRAINT fk_plugin_usage_user_user_id
        FOREIGN KEY (user_id)
            REFERENCES USERS (id);

ALTER TABLE PLUGIN_USAGE
    ADD CONSTRAINT fk_plugin_usage_plugin_plugin_id
        FOREIGN KEY (plugin_id)
            REFERENCES PLUGIN (id);