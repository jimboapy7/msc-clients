-- =====================================================
-- Script completo para crear la estructura de la base de datos
-- Microservicio de Clientes (msc-clientes)
-- =====================================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS msc_clientes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE msc_clientes;

-- =====================================================
-- V1: Crear tabla de clientes
-- =====================================================
CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age >= 0 AND age <= 150),
    birth_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_age (age),
    INDEX idx_birth_date (birth_date),
    INDEX idx_full_name (first_name, last_name)
);

-- =====================================================
-- V2: Crear tablas de usuarios y roles para autenticación JWT
-- =====================================================

-- Crear tabla de usuarios
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Crear tabla de roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de relación usuario-roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Insertar roles por defecto
INSERT INTO roles (name, description) VALUES 
('ROLE_USER', 'Standard user role'),
('ROLE_ADMIN', 'Administrator role');

-- Insertar usuario administrador por defecto (password: admin123)
INSERT INTO users (username, password, email, enabled) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9P2.nRuvkrxqMTu', 'admin@msc-clientes.com', true);

-- Asignar rol de administrador al usuario por defecto
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 2);

-- =====================================================
-- V3: Insertar datos de ejemplo y optimizaciones
-- =====================================================

-- Insertar datos de ejemplo de clientes
INSERT INTO clients (first_name, last_name, age, birth_date) VALUES 
('Juan', 'Pérez', 35, '1989-03-15'),
('María', 'González', 28, '1996-07-22'),
('Carlos', 'Rodríguez', 42, '1982-11-08'),
('Ana', 'Martínez', 31, '1993-01-30'),
('Luis', 'López', 25, '1999-05-12'),
('Carmen', 'Sánchez', 38, '1986-09-18'),
('Pedro', 'Ramírez', 29, '1995-12-03'),
('Laura', 'Torres', 33, '1991-04-25'),
('Miguel', 'Flores', 27, '1997-08-14'),
('Isabel', 'Herrera', 36, '1988-02-07');

-- Crear índices adicionales para optimización de rendimiento
CREATE INDEX idx_clients_created_at ON clients(created_at);
CREATE INDEX idx_clients_updated_at ON clients(updated_at);

-- Crear vista para estadísticas de clientes (útil para el endpoint de métricas)
CREATE VIEW client_statistics AS
SELECT 
    COUNT(*) as total_clients,
    AVG(age) as average_age,
    MIN(age) as min_age,
    MAX(age) as max_age,
    STDDEV(age) as age_standard_deviation,
    COUNT(CASE WHEN age < 30 THEN 1 END) as clients_under_30,
    COUNT(CASE WHEN age BETWEEN 30 AND 50 THEN 1 END) as clients_30_to_50,
    COUNT(CASE WHEN age > 50 THEN 1 END) as clients_over_50
FROM clients;

-- =====================================================
-- Crear tabla de control de versiones de Flyway (opcional)
-- =====================================================
CREATE TABLE IF NOT EXISTS flyway_schema_history (
    installed_rank INT NOT NULL,
    version VARCHAR(50),
    description VARCHAR(200) NOT NULL,
    type VARCHAR(20) NOT NULL,
    script VARCHAR(1000) NOT NULL,
    checksum INT,
    installed_by VARCHAR(100) NOT NULL,
    installed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    execution_time INT NOT NULL,
    success BOOLEAN NOT NULL,
    PRIMARY KEY (installed_rank),
    INDEX flyway_schema_history_s_idx (success)
);

-- =====================================================
-- Verificar la estructura creada
-- =====================================================
SHOW TABLES;

-- Verificar datos insertados
SELECT 'Clientes insertados:' as info, COUNT(*) as count FROM clients;
SELECT 'Usuarios creados:' as info, COUNT(*) as count FROM users;
SELECT 'Roles creados:' as info, COUNT(*) as count FROM roles;

-- Mostrar estadísticas de clientes
SELECT * FROM client_statistics;
