CREATE OR REPLACE FUNCTION count_matching_in_array(tags_json JSONB, words TEXT[]) RETURNS INTEGER AS
$function$
BEGIN
    RETURN (SELECT COUNT(*)
            FROM jsonb_array_elements_text(tags_json) AS tag(value)
            WHERE tag.value = ANY (words));
END;
$function$
    LANGUAGE plpgsql;