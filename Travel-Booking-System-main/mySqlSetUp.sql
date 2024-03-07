-- Create database if it does not exist
CREATE DATABASE IF NOT EXISTS `travel_booking_system` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `travel_booking_system`;

-- Drop tables if they exist IN A SPECIFIC ORDER TO AVOID FOREIGN KEY CONSTRAINTS
DROP TABLE IF EXISTS `payment`, `booking`, `flight`, `airport`, `customer`;

-- Create Customer table
CREATE TABLE customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(20),
    email VARCHAR(50) NOT NULL UNIQUE,
    tel_num VARCHAR(20),
    address VARCHAR(255)
);

-- Create Airport table
CREATE TABLE airport (
    airport_id INT PRIMARY KEY AUTO_INCREMENT,
    airport_name VARCHAR(30),
    airport_location VARCHAR(50)
);


-- Create Flight table
CREATE TABLE flight (
    flight_id INT PRIMARY KEY AUTO_INCREMENT,
    airport_id INT NOT NULL,
    departure_location VARCHAR(50),
    arrival_location VARCHAR(50),
    airline_name VARCHAR(30),
    duration INT,
    flight_cost DECIMAL(10, 2),
    FOREIGN KEY (airport_id) REFERENCES airport(airport_id)
);


-- Create Booking table
CREATE TABLE booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    flight_id INT NOT NULL,
    customer_id INT NOT NULL,
    travel_date DATE NOT NULL,
    travel_time TIME NOT NULL,
    seats INT NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flight(flight_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

-- Create Payment table
CREATE TABLE Payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    amount_paid DECIMAL(10, 2),
    payment_date DATE,
    method VARCHAR(20),
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);

-- Insert data into customer table
INSERT INTO customer (customer_name, email, tel_num, address) VALUES
('John Smith', 'john.smith@example.com', '0851234567', 'Dublin 1'),
('Mary Johnson', 'mary.johnson@example.com', '0862345678', 'Dublin 2'),
('James Murphy', 'james.murphy@example.com', '0873456789', 'Dublin 3'),
('Sarah O\'Brien', 'sarah.obrien@example.com', '0834567890', 'Dublin 4'),
('Michael Collins', 'michael.collins@example.com', '0865678901', 'Dublin 5'),
('Aoife O\'Connor', 'aoife.oconnor@example.com', '0876789012', 'Dublin 6'),
('David Walsh', 'david.walsh@example.com', '0857890123', 'Dublin 7'),
('Sinead Ryan', 'sinead.ryan@example.com', '0868901234', 'Dublin 8'),
('Tommy Byrne', 'tommy.byrne@example.com', '0879012345', 'Dublin 9'),
('Katie Kennedy', 'katie.kennedy@example.com', '0850123456', 'Dublin 10');

-- Insert data into airport table
INSERT INTO airport (airport_name, airport_location) VALUES
('Dublin Airport', 'Dublin'),
('Cork Airport', 'Cork'),
('Shannon Airport', 'Limerick'),
('Galway Airport', 'Galway'),
('Waterford Airport', 'Waterford'),
('Belfast International Airport', 'Belfast'),
('Belfast City Airport', 'Belfast'),
('London Heathrow Airport', 'London'),
('London Gatwick Airport', 'London'),
('Manchester Airport', 'Manchester'),
('Birmingham Airport', 'Birmingham'),
('Edinburgh Airport', 'Edinburgh'),
('Glasgow Airport', 'Glasgow'),
('Paris Charles de Gaulle Airport', 'Paris'),
('Paris Orly Airport', 'Paris'),
('Amsterdam Airport Schiphol', 'Amsterdam'),
('Frankfurt Airport', 'Frankfurt'),
('Barcelona Airport', 'Barcelona'),
('Rome Fiumicino Airport', 'Rome'),
('Milan Malpensa Airport', 'Milan');

-- Insert data into flight table
INSERT INTO flight (airport_id, departure_location, arrival_location, airline_name, duration, flight_cost) VALUES
(1, 'Dublin', 'London', 'Ryanair', 120, 100.50),
(1, 'Dublin', 'Paris', 'Aer Lingus', 150, 200.25),
(1, 'Dublin', 'Barcelona', 'Ryanair', 180, 120.75),
(1, 'Dublin', 'Frankfurt', 'Lufthansa', 240, 350.00),
(1, 'Dublin', 'Manchester', 'Aer Lingus', 90, 80.50),
(2, 'Cork', 'London', 'Ryanair', 100, 90.25),
(2, 'Cork', 'Paris', 'Aer Lingus', 140, 175.00),
(2, 'Cork', 'Amsterdam', 'KLM', 180, 250.75),
(2, 'Cork', 'Manchester', 'Ryanair', 80, 70.00),
(3, 'Limerick', 'London', 'Ryanair', 120, 95.50),
(3, 'Limerick', 'Paris', 'Aer Lingus', 160, 180.25),
(4, 'Galway', 'London', 'Ryanair', 130, 110.75),
(4, 'Galway', 'Paris', 'Ryanair', 170, 150.00),
(5, 'Waterford', 'London', 'Aer Lingus', 110, 105.25),
(6, 'Belfast', 'London', 'EasyJet', 80, 65.00),
(6, 'Belfast', 'Amsterdam', 'KLM', 160, 225.50),
(7, 'Belfast', 'Manchester', 'Aer Lingus', 90, 75.00),
(8, 'London', 'Paris', 'British Airways', 120, 225.75),
(8, 'London', 'Amsterdam', 'EasyJet', 100, 125.50),
(8, 'London', 'Frankfurt', 'British Airways', 180, 275.00),
(8, 'London', 'Barcelona', 'Ryanair', 140, 145.25),
(8, 'London', 'Rome', 'EasyJet', 200, 350.50),
(9, 'London', 'Barcelona', 'EasyJet', 150, 135.00),
(9, 'London', 'Rome', 'Ryanair', 210, 375.75),
(10, 'Manchester', 'Paris', 'British Airways', 110, 200.50),
(10, 'Manchester', 'Amsterdam', 'KLM', 150, 250.25),
(10, 'Manchester', 'Frankfurt', 'British Airways', 170, 300.75),
(10, 'Manchester', 'Barcelona', 'Ryanair', 130, 130.00),
(10, 'Manchester', 'Rome', 'EasyJet', 190, 325.25),
(11, 'Birmingham', 'Paris', 'British Airways', 100, 190.50),
(11, 'Birmingham', 'Amsterdam', 'KLM', 140, 240.25),
(11, 'Birmingham', 'Frankfurt', 'British Airways', 160, 290.75),
(11, 'Birmingham', 'Barcelona', 'Ryanair', 120, 120.00),
(11, 'Birmingham', 'Rome', 'EasyJet', 180, 310.25),
(12, 'Edinburgh', 'Paris', 'British Airways', 90, 180.50),
(12, 'Edinburgh', 'Amsterdam', 'KLM', 130, 230.25),
(12, 'Edinburgh', 'Frankfurt', 'British Airways', 150, 280.75),
(12, 'Edinburgh', 'Barcelona', 'Ryanair', 110, 110.00),
(12, 'Edinburgh', 'Rome', 'EasyJet', 170, 300.25),
(13, 'Glasgow', 'Paris', 'British Airways', 80, 170.50),
(13, 'Glasgow', 'Amsterdam', 'KLM', 120, 220.25),
(13, 'Glasgow', 'Frankfurt', 'British Airways', 140, 270.75),
(13, 'Glasgow', 'Barcelona', 'Ryanair', 100, 100.00),
(13, 'Glasgow', 'Rome', 'EasyJet', 160, 290.25),
(14, 'Paris', 'Barcelona', 'Ryanair', 120, 120.00),
(14, 'Paris', 'Rome', 'EasyJet', 180, 310.25),
(15, 'Paris', 'Barcelona', 'EasyJet', 150, 135.00),
(15, 'Paris', 'Rome', 'Ryanair', 210, 375.75),
(16, 'Amsterdam', 'Barcelona', 'Ryanair', 120, 120.00),
(16, 'Amsterdam', 'Rome', 'EasyJet', 180, 310.25),
(17, 'Frankfurt', 'Barcelona', 'EasyJet', 150, 135.00),
(17, 'Frankfurt', 'Rome', 'Ryanair', 210, 375.75),
(18, 'Barcelona', 'Rome', 'Ryanair', 120, 120.00),
(19, 'Rome', 'Milan', 'Ryanair', 60, 60.00),
(20, 'Milan', 'Rome', 'Ryanair', 60, 60.00);

INSERT INTO booking (flight_id, customer_id, travel_date, travel_time, seats) VALUES
(1, 2, '2023-04-01', '08:00:00', 2),
(2, 3, '2023-04-01', '12:30:00', 1),	
(3, 5, '2023-04-02', '10:30:00', 1),
(4, 2, '2023-04-02', '16:00:00', 2),
(5, 5, '2023-04-03', '10:00:00', 1),
(6, 9, '2023-04-03', '14:45:00', 3),
(7, 1, '2023-04-04', '20:15:00', 2),
(8, 5, '2023-04-05', '07:30:00', 1),
(9, 3, '2023-04-05', '09:00:00', 1),
(10, 1, '2023-04-06', '10:00:00', 2),
(11, 8, '2023-04-06', '11:30:00', 4),
(12, 4, '2023-04-07', '12:00:00', 1),
(14, 4, '2023-04-07', '16:15:00', 2),
(17, 7, '2023-04-08', '18:45:00', 1),
(19, 10, '2023-04-09', '07:00:00', 3),
(20, 6, '2023-04-10', '12:30:00', 2);

-- Insert data into payment table
INSERT INTO payment (booking_id, amount_paid, payment_date, method) VALUES
(1, 201.00, '2023-04-01', 'credit card'),
(2, 120.75, '2023-04-02', 'debit card'),
(3, 362.25, '2023-04-03', 'paypal'),
(4, 201.00, '2023-04-04', 'credit card'),
(5, 120.75, '2023-04-05', 'debit card'),
(6, 482.00, '2023-04-06', 'paypal'),
(7, 201.00, '2023-04-07', 'credit card'),
(8, 120.75, '2023-04-08', 'debit card'),
(9, 723.75, '2023-04-09', 'paypal'),
(10, 241.50, '2023-04-10', 'credit card'),
(11, 482.00, '2023-04-11', 'debit card'),
(12, 120.75, '2023-04-12', 'paypal'),
(14, 241.50, '2023-04-13', 'credit card');