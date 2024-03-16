CREATE TABLE IF NOT EXISTS WEBRESOURCE
(
    id               UUID                     NOT NULL PRIMARY KEY,
    create_date      TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL,
    version          BIGINT                   NOT NULL,

    name             VARCHAR(500)              NOT NULL,
    description      VARCHAR(500),
    status           VARCHAR(500)              NOT NULL,
    plugin_id        UUID                     NOT NULL
);

ALTER TABLE WEBRESOURCE
    ADD CONSTRAINT fk_webresource_plugin_plugin_id
        FOREIGN KEY (plugin_id)
            REFERENCES PLUGIN (id);

CREATE UNIQUE INDEX unq_idx_plugin_id_name ON WEBRESOURCE (plugin_id, name);