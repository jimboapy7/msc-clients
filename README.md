# Microservicoi de Clientes

Este es un microservicio RESTFULL desarrollado con Java Spring Boot siguiendo los principios de Clean Architecture, fue diseñado para ser cloud-native, stateless y escalable horizontalmente

#Arquitectura

Este proyecto implementa Clean Architecture con las siguientes capas:

- Domain: Entidades de negocio, objetos de valor y contratos de repositorio
- Application: Casos de uso y lógica de aplicación
- Infrastructure: Implementaciones de repositorios, seguridad, configuración
- Interface: Controladores REST, DTOs y mappers

# Características

- Clean Architecture con separación clara de responsabilidades
- Autenticación JWT con Spring Security
- Base de datos MySQL con Spring Data JPA
- Migraciones con Flyway
- Documentación API con Swagger/OpenAPI 3
- Validación con Bean Validation (@Valid)
- Manejo global de errores con @ControllerAdvice
- Observabilidad con OpenTelemetry
- Logs estructurados en formato JSON
- Pruebas unitarias con JUnit 5 y Mockito
- Pruebas de integración con Spring Boot Test
- Containerización con Docker
- Patrones de diseño: Repository, Factory, Builder, Singleton

# Requisitos Previos

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Docker 

#Configuración Local

# 1. Clonar el repositorio


git clone https://github.com/jimboapy7/msc-clients.git
cd msc-clientes


# 2. Configurar base de datos MySQL


CREATE DATABASE msc_clientes;
CREATE USER 'msc_user'@'localhost' IDENTIFIED BY 'msc_password';
GRANT ALL PRIVILEGES ON msc_clientes.* TO 'msc_user'@'localhost';
FLUSH PRIVILEGES;


# 3. Configurar variables de entorno


export DB_USERNAME=msc_user
export DB_PASSWORD=msc_password
export JWT_SECRET=mySecretKey123456789012345678901234567890


# 4. Ejecutar migraciones de base de datos


mvn flyway:migrate


# 5. Compilar y ejecutar la aplicación


mvn clean install
mvn spring-boot:run


La aplicación estará disponible en: `http://localhost:8080/api/v1`
