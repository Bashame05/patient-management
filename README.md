# Patient Management System

A robust, microservices-based system designed to manage patient information, handle billing, process analytics, and ensure secure access to healthcare data. Built with modern Java 21 and Spring Boot, the system uses gRPC for high-performance inter-service communication and Apache Kafka for event-driven architecture.

## 🏗️ Architecture

The system is composed of several decoupled microservices to ensure scalability and maintainability:

- **API Gateway**: The unified entry point for all external client requests. It routes traffic to the appropriate backend services.
- **Auth Service**: Manages user authentication and authorization, ensuring secure access to sensitive patient and billing data.
- **Patient Service**: The core service that manages patient records, medical histories, and related demographic data.
- **Billing Service**: Handles invoicing, patient billing, and payment tracking.
- **Analytics Service**: Aggregates data from other services to provide reporting and business intelligence capabilities.

### Communication
- **Synchronous**: REST APIs (via API Gateway) and gRPC (for high-speed internal service-to-service communication).
- **Asynchronous**: Apache Kafka is used for event streaming (e.g., notifying the Analytics or Billing service when a new patient is registered).

## 💻 Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5+
- **Inter-service Communication**: gRPC, Protocol Buffers (Protobuf)
- **Message Broker**: Apache Kafka
- **Databases**: Relational databases (MySQL, PostgreSQL) & H2 (for testing)
- **Data Access**: Spring Data JPA
- **Build Tool**: Maven

## 🚀 Getting Started

### Prerequisites

To run this project locally, you will need the following installed:
- **Java 21**
- **Maven 3.8+**
- **Docker & Docker Compose** (Recommended for running Kafka and Databases)
- *Optional: Protoc compiler (handled automatically by `os-maven-plugin` on most OS)*

### Building the Project

Since this is a multi-module or multi-service project using gRPC, you need to compile the Protocol Buffers files into Java classes.

Navigate to each service directory (e.g., `patient-service`) and run:
```bash
mvn clean install
```
This will download dependencies, trigger the `protobuf-maven-plugin` to generate the gRPC stubs, and build the application jars.

### Running the Services

1. **Start Infrastructure**: Start your databases (MySQL/PostgreSQL) and Apache Kafka cluster.
2. **Start Microservices**: You can run each service independently using your IDE or via the Maven Spring Boot plugin:
   ```bash
   mvn spring-boot:run
   ```
   *Note: Ensure you start the Auth Service and API Gateway alongside the domain services (Patient, Billing, Analytics) to enable full functionality.*

## 📂 Project Structure

- `/api-gateway` - Spring Cloud Gateway or similar routing logic.
- `/auth-service` - Security, JWT generation, and user validation.
- `/patient-service` - Patient management logic, JPA entities, and gRPC servers/clients.
- `/billing-service` - Financial transactions and invoice generation.
- `/analytics-service` - Event consumption and reporting.
- `/api-requests` - Postman or HTTP client request collections for testing REST endpoints.
- `/grpc-requests` - Payload and script definitions for testing gRPC endpoints.
- `/db_volumes` - Docker volumes for persistent database storage (if using containers).

## 📄 API Documentation
Services include OpenAPI/Swagger dependencies. Once running, you can typically access the REST API documentation via:
`http://localhost:<SERVICE_PORT>/swagger-ui.html`

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
