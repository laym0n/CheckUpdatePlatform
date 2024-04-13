CREATE TABLE IF NOT EXISTS TASK
(
    id                   UUID                     NOT NULL PRIMARY KEY,
    create_date          TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date     TIMESTAMP WITH TIME ZONE NOT NULL,
    version              BIGINT                   NOT NULL,

    type                 VARCHAR(50)              NOT NULL,
    image_paths          VARCHAR(500),
    description          VARCHAR(500),
    distribution_methods VARCHAR(500)             NOT NULL,
    decision             VARCHAR(50),
    comment              VARCHAR(500),
    tags                 JSONB,
    logo_path            VARCHAR(50),
    plugin_id            UUID                     NOT NULL
);

ALTER TABLE TASK
    ADD CONSTRAINT fk_plugin_update_request_plugin_plugin_id
        FOREIGN KEY (plugin_id)
            REFERENCES PLUGIN (id);