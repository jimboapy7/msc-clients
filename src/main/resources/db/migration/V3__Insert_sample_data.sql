-- Insert sample client data for testing
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

-- Create additional indexes for performance optimization
CREATE INDEX idx_clients_created_at ON clients(created_at);
CREATE INDEX idx_clients_updated_at ON clients(updated_at);

-- Create a view for client statistics (useful for metrics endpoint)
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
