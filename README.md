# Template-BE (Backend Template)

## Overview
This project, `template-be`, serves as a Spring Boot backend template. It is designed to streamline the development process by providing a foundational structure for building robust API services. The template leverages several key technologies to facilitate API creation, database interaction, object mapping, and automated documentation.

## Key Technologies
- **API Creation**: Utilizing **Spring Boot** for rapid and efficient backend development.
- **Object Mapping**: **MapStruct** is used for mapping objects between different layers (e.g., DTOs to Entities).
- **Database Interaction**: **Spring Data JPA** for seamless integration with databases, facilitating data persistence and retrieval.
- **Documentation**: **SpringDoc** is employed to automatically generate OpenAPI documentation for the API.
- **Code Simplification**: **Lombok** library is used to reduce boilerplate code like getters, setters, and constructors.

## Getting Started
Follow these steps to get your development environment set up:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/DzakirinMD/template-be.git
   cd template-be
   ```
2. **Build and Run the Container**:
    ```bash
    docker-compose up --build -d
    ```
3. **Stopping the Application**:
    ```bash
    docker-compose down
    ```   
5. **Clear dev environment**
   ```bash
    docker-compose down -v and delete the "docker-data" folder if you want to clear all the dev environment
    ```   

## API Documentation
- **Swagger UI**: Explore and interact with the API's endpoints via Swagger UI. Access it at [Swagger UI](http://localhost:8080/swagger-ui/index.html).
- **API Docs (Web)**: View the API documentation in a web format at [API Docs](http://localhost:8080/api-docs).
- **Download API Docs**: The API documentation can be downloaded in YAML format from [API Docs YAML](http://localhost:8080/api-docs.yaml).
- **Kafka-ui**: The can be viewed from [Kafka-UI](localhost:18080/kafka-ui).

## Kafka-UI
1. Click this this [kafka-ui](http://localhost:18080).
2. Click `Configure new cluster` button.
3. Put the Cluster name as "Localhost".
4. In the Bootstrap Servers the **HOST** and **PORT** is the one in the KAFKA_ADVERTISED_LISTENERS.
5. In this case the host = `template-be-kafka`, port = `9092`.
6. Click `Validate` and the `Submit` once the connection is established.

## Further enhancement Idea
Common Modules:
- Create a common logging module for centralized logging functionality.
- Find a way to split the security module from the user module.
- Create a common-library and move EventWrapper and shared DTOs into it.

Microservices Refactoring:
- Separate user-service from account-service to enhance modularity and maintainability.
- Develop a new microservice using Go (country-information-service) with GraphQL.
- Create a new microservice for authentication (auth-service).

Technical Improvements:
- Convert Java 8 Instant to LocalDateTime where necessary.

## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/DzakirinMD/template-be/blob/main/LICENSE) file for details.