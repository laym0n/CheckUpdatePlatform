CREATE TABLE IF NOT EXISTS TAG
(
    id               UUID                     NOT NULL PRIMARY KEY,
    create_date      TIMESTAMP WITH TIME ZONE NOT NULL,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL,
    version          BIGINT                   NOT NULL,

    data             VARCHAR(50)              NOT NULL
);

CREATE UNIQUE INDEX idx_tag_data ON TAG (data);