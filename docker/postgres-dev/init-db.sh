#!/bin/bash
set -e

# Create schema 'user-service' after the database is created, if it doesn't exist
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DO \$\$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'user-service') THEN
            CREATE SCHEMA "user-service";
        END IF;
    END
    \$\$;
EOSQL
