ALTER TABLE PLUGIN
    ADD COLUMN
        status VARCHAR(50) NOT NULL;

ALTER TABLE PLUGIN
    ADD COLUMN
        logo_path VARCHAR(50);

ALTER TABLE PLUGIN
    ADD COLUMN
        tags JSONB;

ALTER TABLE PLUGIN
    ALTER COLUMN
        distribution_methods DROP NOT NULL;

ALTER TABLE PLUGIN
    ALTER COLUMN
        description TYPE VARCHAR(500);

ALTER TABLE PLUGIN
    ALTER COLUMN
        image_paths TYPE VARCHAR(500);