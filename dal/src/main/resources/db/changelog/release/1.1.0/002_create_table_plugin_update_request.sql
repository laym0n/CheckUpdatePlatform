CREATE TABLE IF NOT EXISTS PLUGIN_UPDATE_REQUEST
(
    id                   UUID                     NOT NULL PRIMARY KEY,
    create_date          TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date     TIMESTAMP WITH TIME ZONE NOT NULL,
    version              BIGINT                   NOT NULL,

    type                 VARCHAR(50)              NOT NULL,
    image_paths          VARCHAR(50),
    description          VARCHAR(50),
    distribution_methods VARCHAR(500)             NOT NULL,
    plugin_id            UUID                     NOT NULL
);

ALTER TABLE PLUGIN_UPDATE_REQUEST
    ADD CONSTRAINT fk_plugin_update_request_plugin_plugin_id
        FOREIGN KEY (plugin_id)
            REFERENCES PLUGIN (id);