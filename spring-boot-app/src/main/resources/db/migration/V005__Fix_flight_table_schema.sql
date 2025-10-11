-- Fix flight table schema to match legacy DAO expectations
-- Temporarily disable foreign key checks to allow table recreation
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS flight;
SET FOREIGN_KEY_CHECKS = 1;

-- Create Flight table with correct column names for legacy DAO
CREATE TABLE flight (
    flight_id INT PRIMARY KEY AUTO_INCREMENT,
    flight_number VARCHAR(10) NOT NULL UNIQUE,
    airport_number VARCHAR(10) NOT NULL,
    departure_location VARCHAR(50),
    departure_time TIME,
    arrival_location VARCHAR(50),
    arrival_time TIME,
    airline_name VARCHAR(30),
    flight_cost DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_flight_number (flight_number),
    INDEX idx_airport_number (airport_number),
    INDEX idx_departure_location (departure_location),
    INDEX idx_arrival_location (arrival_location),
    FOREIGN KEY (airport_number) REFERENCES airport(airport_number)
);

-- Insert sample flight data with correct columns
INSERT INTO flight (flight_number, airport_number, departure_location, departure_time, arrival_location, arrival_time, airline_name, flight_cost) VALUES
('AA101', 'LAX', 'Los Angeles', '08:00:00', 'New York', '16:30:00', 'American Airlines', 299.99),
('UA202', 'JFK', 'New York', '10:00:00', 'Chicago', '12:45:00', 'United Airlines', 199.99),
('DL303', 'ORD', 'Chicago', '14:00:00', 'Dallas', '16:30:00', 'Delta Airlines', 249.99),
('SW404', 'DFW', 'Dallas', '09:00:00', 'Atlanta', '12:00:00', 'Southwest Airlines', 179.99),
('AA505', 'ATL', 'Atlanta', '11:00:00', 'San Francisco', '14:30:00', 'American Airlines', 349.99),
('UA606', 'SFO', 'San Francisco', '16:00:00', 'Seattle', '18:15:00', 'United Airlines', 159.99),
('DL707', 'SEA', 'Seattle', '07:30:00', 'Miami', '15:45:00', 'Delta Airlines', 399.99),
('SW808', 'MIA', 'Miami', '13:00:00', 'Las Vegas', '15:30:00', 'Southwest Airlines', 279.99),
('AA909', 'LAS', 'Las Vegas', '17:00:00', 'Phoenix', '18:15:00', 'American Airlines', 89.99),
('UA010', 'PHX', 'Phoenix', '19:00:00', 'Los Angeles', '20:30:00', 'United Airlines', 129.99);