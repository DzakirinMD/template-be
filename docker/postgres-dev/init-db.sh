#!/bin/bash
set -e

# Create schemas 'user-service' and 'auth-service' after the database is created, if they don't exist
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DO \$\$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'user-service') THEN
            CREATE SCHEMA "user-service";
        END IF;
        IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'auth-service') THEN
            CREATE SCHEMA "auth-service";
        END IF;
    END
    \$\$;
EOSQL
