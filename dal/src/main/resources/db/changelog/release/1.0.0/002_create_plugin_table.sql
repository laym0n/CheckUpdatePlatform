CREATE TABLE IF NOT EXISTS PLUGIN
(
    id                   UUID                     NOT NULL PRIMARY KEY,
    create_date          TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date     TIMESTAMP WITH TIME ZONE NOT NULL,
    version              BIGINT                   NOT NULL,

    name                 VARCHAR(50)              NOT NULL,
    base_url             VARCHAR(500)             NOT NULL,
    access_token         VARCHAR(500)             NOT NULL,
    image_paths          VARCHAR(50),
    description          VARCHAR(50),
    distribution_methods VARCHAR(500)             NOT NULL,
    owner_user_id        UUID                     NOT NULL
);

ALTER TABLE PLUGIN
    ADD CONSTRAINT fk_plugin_user_owner_user_id
        FOREIGN KEY (owner_user_id)
            REFERENCES USERS (id);

CREATE UNIQUE INDEX idx_plugin_name ON PLUGIN (name);