FROM postgres:latest

ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_DB=template_be_db

EXPOSE 5432

# Create a custom initialization script
COPY ./init-db.sh /docker-entrypoint-initdb.d/

# Make sure the script is executable
RUN chmod +x /docker-entrypoint-initdb.d/init-db.sh
