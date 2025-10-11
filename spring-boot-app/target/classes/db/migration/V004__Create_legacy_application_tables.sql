-- Create legacy application tables for airports, flights, customers, bookings, and payments

-- Create Airport table
CREATE TABLE IF NOT EXISTS airport (
    airport_id INT PRIMARY KEY AUTO_INCREMENT,
    airport_number VARCHAR(10) NOT NULL UNIQUE,
    airport_name VARCHAR(255) NOT NULL,
    airport_location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_airport_number (airport_number),
    INDEX idx_airport_name (airport_name)
);

-- Create Flight table
CREATE TABLE IF NOT EXISTS flight (
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

-- Create Customer table  
CREATE TABLE IF NOT EXISTS customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_number VARCHAR(20) NOT NULL UNIQUE,
    customer_name VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    tel_num VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_customer_number (customer_number),
    INDEX idx_customer_email (email),
    INDEX idx_customer_name (customer_name)
);

-- Create Booking table
CREATE TABLE IF NOT EXISTS booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_number VARCHAR(20) NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    flight_id INT NOT NULL,
    passenger_name VARCHAR(255) NOT NULL,
    seat_number VARCHAR(10),
    booking_status VARCHAR(20) DEFAULT 'CONFIRMED',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    travel_date DATE NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_booking_number (booking_number),
    INDEX idx_customer_id (customer_id),
    INDEX idx_flight_id (flight_id),
    INDEX idx_booking_status (booking_status),
    INDEX idx_travel_date (travel_date),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (flight_id) REFERENCES flight(flight_id)
);

-- Create Payment table
CREATE TABLE IF NOT EXISTS payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_number VARCHAR(20) NOT NULL UNIQUE,
    booking_id INT NOT NULL,
    amount_paid DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) DEFAULT 'CREDIT_CARD',
    payment_status VARCHAR(20) DEFAULT 'COMPLETED',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_payment_number (payment_number),
    INDEX idx_booking_id (booking_id),
    INDEX idx_payment_status (payment_status),
    INDEX idx_payment_date (payment_date),
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);

-- Insert sample airport data
INSERT IGNORE INTO airport (airport_number, airport_name, airport_location) VALUES
('LAX', 'Los Angeles International Airport', 'Los Angeles, CA'),
('JFK', 'John F. Kennedy International Airport', 'New York, NY'),
('ORD', 'Chicago O\'Hare International Airport', 'Chicago, IL'),
('DFW', 'Dallas/Fort Worth International Airport', 'Dallas, TX'),
('ATL', 'Hartsfield-Jackson Atlanta International Airport', 'Atlanta, GA'),
('SFO', 'San Francisco International Airport', 'San Francisco, CA'),
('SEA', 'Seattle-Tacoma International Airport', 'Seattle, WA'),
('MIA', 'Miami International Airport', 'Miami, FL'),
('LAS', 'McCarran International Airport', 'Las Vegas, NV'),
('PHX', 'Phoenix Sky Harbor International Airport', 'Phoenix, AZ');

-- Insert sample flight data
INSERT IGNORE INTO flight (flight_number, airport_number, departure_location, departure_time, arrival_location, arrival_time, airline_name, flight_cost) VALUES
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

-- Insert sample customer data
INSERT IGNORE INTO customer (customer_number, customer_name, email, tel_num, address) VALUES
('CUST001', 'John Smith', 'john.smith@email.com', '555-0101', '123 Main St, Los Angeles, CA'),
('CUST002', 'Jane Doe', 'jane.doe@email.com', '555-0102', '456 Oak Ave, New York, NY'),
('CUST003', 'Bob Johnson', 'bob.johnson@email.com', '555-0103', '789 Pine St, Chicago, IL'),
('CUST004', 'Alice Brown', 'alice.brown@email.com', '555-0104', '321 Elm St, Dallas, TX'),
('CUST005', 'Charlie Davis', 'charlie.davis@email.com', '555-0105', '654 Maple Ave, Atlanta, GA');