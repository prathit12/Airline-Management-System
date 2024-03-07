-- Create database if it does not exist
CREATE DATABASE IF NOT EXISTS `travel_booking_system` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `travel_booking_system`;

-- Drop tables if they exist IN A SPECIFIC ORDER TO AVOID FOREIGN KEY CONSTRAINTS
DROP TABLE IF EXISTS `payment`, `booking`, `flight`, `airport`, `customer`;

-- Create Customer table
CREATE TABLE customer (
    customer_number VARCHAR(10) PRIMARY KEY,
    customer_id INT AUTO_INCREMENT UNIQUE,
    customer_name VARCHAR(20),
    email VARCHAR(50) NOT NULL UNIQUE,
    tel_num VARCHAR(20),
    address VARCHAR(255)
);

-- Create Airport table
CREATE TABLE airport (
    airport_number VARCHAR(10) PRIMARY KEY,
    airport_id INT AUTO_INCREMENT UNIQUE,
    airport_name VARCHAR(30),
    airport_location VARCHAR(50)
);


-- Create Flight table
-- CREATE TABLE flight (
--     flight_number VARCHAR(10) PRIMARY KEY,
--     flight_id INT AUTO_INCREMENT UNIQUE,
--     airport_number VARCHAR(20) NOT NULL,
--     departure_location VARCHAR(50),
--     arrival_location VARCHAR(50),
--     airline_name VARCHAR(30),
--     duration INT,
--     flight_cost DECIMAL(10, 2),
--     FOREIGN KEY (airport_number) REFERENCES airport(airport_number)
-- );
CREATE TABLE flight (
    flight_number VARCHAR(10) PRIMARY KEY,
    flight_id INT AUTO_INCREMENT UNIQUE,
    airport_number VARCHAR(20) NOT NULL,
    departure_location VARCHAR(50),
    departure_time TIME,
    arrival_location VARCHAR(50),
    arrival_time TIME,
    airline_name VARCHAR(30),
    flight_cost DECIMAL(10, 2),
    FOREIGN KEY (airport_number) REFERENCES airport(airport_number)
);


-- Create Booking table
CREATE TABLE booking (
    booking_number VARCHAR(10) PRIMARY KEY,
    booking_id INT AUTO_INCREMENT UNIQUE,
    flight_number VARCHAR(20) NOT NULL,
    customer_number VARCHAR(20) NOT NULL,
    travel_date DATE NOT NULL,
    seat_number VARCHAR(5) NOT NULL,
    FOREIGN KEY (flight_number) REFERENCES flight(flight_number),
    FOREIGN KEY (customer_number) REFERENCES customer(customer_number)
);


-- Create Payment table
CREATE TABLE Payment (
    payment_number VARCHAR(10) PRIMARY KEY,
    payment_id INT AUTO_INCREMENT UNIQUE,
    booking_number VARCHAR(20) NOT NULL,
    amount_paid DECIMAL(10, 2),
    payment_date DATE,
    method VARCHAR(20),
    FOREIGN KEY (booking_number) REFERENCES booking(booking_number)
);

-- Insert data into customer table
INSERT INTO customer (customer_number, customer_name, email, tel_num, address) VALUES
('C0001', 'John Smith', 'john.smith@example.com', '0851234567', 'Dublin 1'),
('C0002', 'Mary Johnson', 'mary.johnson@example.com', '0862345678', 'Dublin 2'),
('C0003', 'James Murphy', 'james.murphy@example.com', '0873456789', 'Dublin 3'),
('C0004', 'Sarah O\'Brien', 'sarah.obrien@example.com', '0834567890', 'Dublin 4'),
('C0005', 'Michael Collins', 'michael.collins@example.com', '0865678901', 'Dublin 5'),
('C0006', 'Aoife O\'Connor', 'aoife.oconnor@example.com', '0876789012', 'Dublin 6'),
('C0007', 'David Walsh', 'david.walsh@example.com', '0857890123', 'Dublin 7'),
('C0008', 'Sinead Ryan', 'sinead.ryan@example.com', '0868901234', 'Dublin 8'),
('C0009', 'Tommy Byrne', 'tommy.byrne@example.com', '0879012345', 'Dublin 9'),
('C0010', 'Katie Kennedy', 'katie.kennedy@example.com', '0850123456', 'Dublin 10');

-- Insert data into airport table
INSERT INTO airport (airport_number, airport_name, airport_location) VALUES
('DUB', 'Dublin Airport', 'Dublin'),
('ORK', 'Cork Airport', 'Cork'),
('SNN', 'Shannon Airport', 'Limerick'),
('GWY', 'Galway Airport', 'Galway'),
('WAT', 'Waterford Airport', 'Waterford'),
('BFS', 'Belfast International Airport', 'Belfast'),
('BHD', 'Belfast City Airport', 'Belfast'),
('LHR', 'London Heathrow Airport', 'London'),
('LGW', 'London Gatwick Airport', 'London'),
('MAN', 'Manchester Airport', 'Manchester'),
('BHX', 'Birmingham Airport', 'Birmingham'),
('EDI', 'Edinburgh Airport', 'Edinburgh'),
('GLA', 'Glasgow Airport', 'Glasgow'),
('ORY', 'Paris Orly Airport', 'Paris'),
('AMS', 'Amsterdam Airport Schiphol', 'Amsterdam'),
('FRA', 'Frankfurt Airport', 'Frankfurt'),
('BCN', 'Barcelona Airport', 'Barcelona'),
('FCO', 'Rome Fiumicino Airport', 'Rome'),
('MXP', 'Milan Malpensa Airport', 'Milan'),
('LIS', 'Lisbon Airport', 'Lisbon'),
('OPO', 'Porto Airport', 'Porto'),
('ATH', 'Athens International Airport', 'Athens'),
('IST', 'Istanbul Airport', 'Istanbul'),
('PRG', 'Prague Airport', 'Prague'),
('BUD', 'Budapest Airport', 'Budapest'),
('VIE', 'Vienna International Airport', 'Vienna'),
('MAD', 'Madrid-Barajas Airport', 'Madrid'),
('LTN', 'London Luton Airport', 'London'),
('STN', 'London Stansted Airport', 'London'),
('CDG', 'Paris-Charles de Gaulle Airport', 'Paris'),
('MRS', 'Marseille Provence Airport', 'Marseille'),
('BVA', 'Beauvais-Tillé Airport', 'Paris'),
('NCE', 'Nice Côte d\'Azur Airport', 'Nice'),
('MUC', 'Munich Airport', 'Munich'),
('TXL', 'Berlin Tegel Airport', 'Berlin'),
('SXF', 'Berlin Schönefeld Airport', 'Berlin'),
('CRL', 'Brussels South Charleroi Airport', 'Brussels'),
('BRU', 'Brussels Airport', 'Brussels'),
('RTM', 'Rotterdam The Hague Airport', 'Rotterdam'),
('ARN', 'Stockholm Arlanda Airport', 'Stockholm'),
('BMA', 'Stockholm Bromma Airport', 'Stockholm'),
('OSL', 'Oslo Airport', 'Oslo'),
('TRF', 'Sandefjord Airport, Torp', 'Oslo'),
('HEL', 'Helsinki-Vantaa Airport', 'Helsinki'),
('BTS', 'Bratislava Airport', 'Bratislava'),
('KRK', 'Kraków John Paul II International Airport', 'Kraków'),
('WAW', 'Warsaw Chopin Airport', 'Warsaw'),
('FAO', 'Faro Airport', 'Faro'),
('FNC', 'Madeira Airport', 'Funchal'),
('LPA', 'Gran Canaria Airport', 'Las Palmas'),
('TFS', 'Tenerife South Airport', 'Tenerife'),
('ACE', 'Lanzarote Airport', 'Arrecife'),
('AGP', 'Málaga Airport', 'Málaga');

-- Insert data into flight table
-- INSERT INTO flight (flight_number, airport_number, departure_location, arrival_location, airline_name, duration, flight_cost) VALUES
-- ('FR1001', 'DUB', 'Dublin', 'London', 'Ryanair', 120, 100.50),
-- ('EI2001', 'DUB', 'Dublin', 'Paris', 'Aer Lingus', 150, 200.25),
-- ('FR1002', 'DUB', 'Dublin', 'Barcelona', 'Ryanair', 180, 120.75),
-- ('LH3001', 'DUB', 'Dublin', 'Frankfurt', 'Lufthansa', 240, 350.00),
-- ('EI2002', 'DUB', 'Dublin', 'Manchester', 'Aer Lingus', 90, 80.50),
-- ('FR2001', 'ORK', 'Cork', 'London', 'Ryanair', 100, 90.25),
-- ('EI3001', 'ORK', 'Cork', 'Paris', 'Aer Lingus', 140, 175.00),
-- ('KL4001', 'ORK', 'Cork', 'Amsterdam', 'KLM', 180, 250.75),
-- ('FR2002', 'ORK', 'Cork', 'Manchester', 'Ryanair', 80, 70.00),
-- ('FR3001', 'SNN', 'Limerick', 'London', 'Ryanair', 120, 95.50),
-- ('EI4001', 'SNN', 'Limerick', 'Paris', 'Aer Lingus', 160, 180.25),
-- ('FR4001', 'GWY', 'Galway', 'London', 'Ryanair', 130, 110.75),
-- ('FR4002', 'GWY', 'Galway', 'Paris', 'Ryanair', 170, 150.00),
-- ('EI5001', 'WAT', 'Waterford', 'London', 'Aer Lingus', 110, 105.25),
-- ('U2001', 'BFS', 'Belfast', 'London', 'EasyJet', 80, 65.00),
-- ('KL5001', 'BFS', 'Belfast', 'Amsterdam', 'KLM', 160, 225.50),
-- ('EI6001', 'BFS', 'Belfast', 'Manchester', 'Aer Lingus', 90, 75.00),
-- ('BA1001', 'LHR', 'London', 'Paris', 'British Airways', 120, 225.75),
-- ('U2002', 'LHR', 'London', 'Amsterdam', 'EasyJet', 100, 125.50),
-- ('BA1002', 'LHR', 'London', 'Frankfurt', 'British Airways', 180, 275.00),
-- ('FR5001', 'LHR', 'London', 'Barcelona', 'Ryanair', 140, 145.25),
-- ('U2003', 'LHR', 'London', 'Rome', 'EasyJet', 200, 350.50),
-- ('FR6001', 'LHR', 'London', 'Barcelona', 'EasyJet', 150, 135.00),
-- ('FR6002', 'LHR', 'London', 'Rome', 'Ryanair', 210, 375.75),
-- ('BA6001', 'CDG', 'Paris', 'London', 'British Airways', 120, 225.75),
-- ('KL10001', 'CDG', 'Paris', 'Amsterdam', 'KLM', 100, 125.50),
-- ('BA6002', 'CDG', 'Paris', 'Frankfurt', 'British Airways', 180, 275.00),
-- ('FR11001', 'CDG', 'Paris', 'Barcelona', 'Ryanair', 140, 145.25),
-- ('U3001', 'CDG', 'Paris', 'Rome', 'EasyJet', 200, 350.50),
-- ('BA7001', 'ORY', 'Paris', 'London', 'British Airways', 150, 135.00),
-- ('KL11001', 'ORY', 'Paris', 'Amsterdam', 'KLM', 210, 375.75),
-- ('BA7002', 'ORY', 'Paris', 'Frankfurt', 'British Airways', 110, 200.50),
-- ('FR12001', 'ORY', 'Paris', 'Barcelona', 'Ryanair', 170, 300.75),
-- ('U3002', 'ORY', 'Paris', 'Rome', 'EasyJet', 130, 130.00),
-- ('BA8001', 'AMS', 'Amsterdam', 'London', 'British Airways', 190, 325.25),
-- ('KL12001', 'AMS', 'Amsterdam', 'Paris', 'KLM', 150, 175.00),
-- ('BA8002', 'AMS', 'Amsterdam', 'Frankfurt', 'British Airways', 210, 375.75),
-- ('FR13001', 'AMS', 'Amsterdam', 'Barcelona', 'Ryanair', 170, 300.75),
-- ('U3003', 'AMS', 'Amsterdam', 'Rome', 'EasyJet', 130, 130.00),
-- ('BA9001', 'FRA', 'Frankfurt', 'London', 'British Airways', 190, 325.25),
-- ('KL13001', 'FRA', 'Frankfurt', 'Paris', 'KLM', 150, 175.00),
-- ('BA9002', 'FRA', 'Frankfurt', 'Amsterdam', 'British Airways', 210, 375.75),
-- ('FR14001', 'FRA', 'Frankfurt', 'Barcelona', 'Ryanair', 170, 300.75),
-- ('U3004', 'FRA', 'Frankfurt', 'Rome', 'EasyJet', 130, 130.00),
-- ('BA10001', 'BCN', 'Barcelona', 'London', 'British Airways', 190, 325.25),
-- ('KL14001', 'BCN', 'Barcelona', 'Paris', 'KLM', 150, 175.00),
-- ('BA10002', 'BCN', 'Barcelona', 'Amsterdam', 'British Airways', 210, 375.75),
-- ('FR15001', 'BCN', 'Barcelona', 'Frankfurt', 'Ryanair', 170, 300.75),
-- ('U3005', 'BCN', 'Barcelona', 'Rome', 'EasyJet', 130, 130.00),
-- ('BA11001', 'FCO', 'Rome', 'London', 'British Airways', 190, 325.25),
-- ('KL15001', 'FCO', 'Rome', 'Paris', 'KLM', 150, 175.00),
-- ('BA11002', 'FCO', 'Rome', 'Amsterdam', 'British Airways', 210, 375.75),
-- ('FR16001', 'FCO', 'Rome', 'Frankfurt', 'Ryanair', 170, 300.75);

INSERT INTO flight (flight_number, airport_number, departure_location, departure_time, arrival_location, arrival_time, airline_name, flight_cost) VALUES
('FR1001', 'DUB', 'Dublin', '06:00:00', 'London', '08:00:00', 'Ryanair', 100.50),
('EI2001', 'DUB', 'Dublin', '07:30:00', 'Paris', '10:00:00', 'Aer Lingus', 200.25),
('FR1002', 'DUB', 'Dublin', '08:00:00', 'Barcelona', '11:00:00', 'Ryanair', 120.75),
('LH3001', 'DUB', 'Dublin', '10:00:00', 'Frankfurt', '13:00:00', 'Lufthansa', 350.00),
('EI2002', 'DUB', 'Dublin', '11:00:00', 'Manchester', '12:30:00', 'Aer Lingus', 80.50),
('FR2001', 'ORK', 'Cork', '09:00:00', 'London', '11:00:00', 'Ryanair', 90.25),
('EI3001', 'ORK', 'Cork', '12:00:00', 'Paris', '14:00:00', 'Aer Lingus', 175.00),
('KL4001', 'ORK', 'Cork', '14:00:00', 'Amsterdam', '17:00:00', 'KLM', 250.75),
('FR2002', 'ORK', 'Cork', '15:00:00', 'Manchester', '16:30:00', 'Ryanair', 70.00),
('FR3001', 'SNN', 'Limerick', '06:30:00', 'London', '08:00:00', 'Ryanair', 95.50),
('EI4001', 'SNN', 'Limerick', '09:00:00', 'Paris', '11:30:00', 'Aer Lingus', 180.25),
('FR4001', 'GWY', 'Galway', '11:00:00', 'London', '13:00:00', 'Ryanair', 110.75),
('FR4002', 'GWY', 'Galway', '13:30:00', 'Paris', '16:00:00', 'Ryanair', 150.00),
('EI5001', 'WAT', 'Waterford', '08:30:00', 'London', '10:00:00', 'Aer Lingus', 105.25),
('U2001', 'BFS', 'Belfast', '09:30:00', 'London', '10:50:00', 'EasyJet', 65.00),
('KL5001', 'BFS', 'Belfast', '14:00:00', 'Amsterdam', '17:00:00', 'KLM', 225.50),
('EI6001', 'BFS', 'Belfast', '10:30:00', 'Manchester', '12:00:00', 'Aer Lingus', 75.00),
('BA1001', 'LHR', 'London', '09:00:00', 'Paris', '10:00:00', 'British Airways', 225.75),
('U2002', 'LHR', 'London', '11:00:00', 'Amsterdam', '13:00:00', 'EasyJet', 125.50),
('BA1002', 'LHR', 'London', '14:00:00', 'Frankfurt', '17:00:00', 'British Airways', 275.00),
('FR5001', 'LHR', 'London', '16:30:00', 'Barcelona', '19:30:00', 'Ryanair', 145.25),
('U2003', 'LHR', 'London', '08:00:00', 'Rome', '11:00:00', 'EasyJet', 350.50),
('FR6001', 'LHR', 'London', '13:00:00', 'Barcelona', '15:30:00', 'EasyJet', 135.00),
('FR6002', 'LHR', 'London', '18:00:00', 'Rome', '21:30:00', 'Ryanair', 375.75),
('BA6001', 'CDG', 'Paris', '10:00:00', 'London', '11:00:00', 'British Airways', 225.75),
('KL10001', 'CDG', 'Paris', '12:00:00', 'Amsterdam', '14:00:00', 'KLM', 125.50),
('BA6002', 'CDG', 'Paris', '16:00:00', 'Frankfurt', '18:00:00', 'British Airways', 275.00),
('FR11001', 'CDG', 'Paris', '14:30:00', 'Barcelona', '16:30:00', 'Ryanair', 145.25),
('U3001', 'CDG', 'Paris', '08:00:00', 'Rome', '11:00:00', 'EasyJet', 350.50),
('BA7001', 'ORY', 'Paris', '11:00:00', 'London', '12:00:00', 'British Airways', 135.00),
('KL11001', 'ORY', 'Paris', '13:00:00', 'Amsterdam', '15:00:00', 'KLM', 375.75),
('BA7002', 'ORY', 'Paris', '17:00:00', 'Frankfurt', '19:00:00', 'British Airways', 200.50),
('FR12001', 'ORY', 'Paris', '15:30:00', 'Barcelona', '17:30:00', 'Ryanair', 300.75),
('U3002', 'ORY', 'Paris', '13:30:00', 'FCO', '15:40:00', 'EasyJet', 130.00),
('BA8001', 'AMS', 'Amsterdam', '15:20:00', 'LHR', '15:50:00', 'British Airways', 325.25),
('KL12001', 'AMS', 'Amsterdam', '14:50:00', 'CDG', '16:20:00', 'KLM', 175.00),
('BA8002', 'AMS', 'Amsterdam', '16:30:00', 'FRA', '18:00:00', 'British Airways', 375.75),
('FR13001', 'AMS', 'Amsterdam', '15:40:00', 'BCN', '18:10:00', 'Ryanair', 300.75),
('U3003', 'AMS', 'Amsterdam', '14:00:00', 'FCO', '16:10:00', 'EasyJet', 130.00),
('BA9001', 'FRA', 'Frankfurt', '14:40:00', 'LHR', '15:10:00', 'British Airways', 325.25),
('KL13001', 'FRA', 'Frankfurt', '15:10:00', 'CDG', '16:40:00', 'KLM', 175.00),
('BA9002', 'FRA', 'Frankfurt', '16:50:00', 'AMS', '18:20:00', 'British Airways', 375.75),
('FR14001', 'FRA', 'Frankfurt', '15:20:00', 'BCN', '17:50:00', 'Ryanair', 300.75),
('U3004', 'FRA', 'Frankfurt', '14:00:00', 'FCO', '16:10:00', 'EasyJet', 130.00),
('BA10001', 'BCN', 'Barcelona', '17:30:00', 'LHR', '19:00:00', 'British Airways', 325.25),
('KL14001', 'BCN', 'Barcelona', '18:00:00', 'CDG', '19:30:00', 'KLM', 175.00),
('BA10002', 'BCN', 'Barcelona', '20:00:00', 'AMS', '22:30:00', 'British Airways', 375.75),
('FR15001', 'BCN', 'Barcelona', '17:10:00', 'FRA', '19:40:00', 'Ryanair', 300.75),
('U3005', 'BCN', 'Barcelona', '14:00:00', 'FCO', '16:10:00', 'EasyJet', 130.00),
('BA11001', 'FCO', 'Rome', '20:40:00', 'LHR', '22:10:00', 'British Airways', 325.25),
('KL15001', 'FCO', 'Rome', '14:00:00', 'CDG', '16:30:00', 'KLM', 175.00),
('BA11002', 'FCO', 'Rome', '09:30:00', 'AMS', '12:15:00', 'British Airways', 375.75),
('FR16001', 'FCO', 'Rome', '16:00:00', 'FRA', '17:50:00', 'Ryanair', 300.75);

INSERT INTO booking (booking_number, flight_number, customer_number, travel_date, seat_number) VALUES
('B0001', 'FR1001', 'C0001', '2023-04-01', '2A'),
('B0002', 'EI2001', 'C0002', '2023-04-01', '3C'),
('B0003', 'FR1002', 'C0003', '2023-04-01', '1F'),
('B0004', 'LH3001', 'C0004', '2023-04-01', '5B'),
('B0005', 'EI2002', 'C0005', '2023-04-02', '4D'),
('B0006', 'FR2001', 'C0006', '2023-04-02', '6C'),
('B0007', 'EI3001', 'C0007', '2023-04-02', '2B'),
('B0008', 'KL4001', 'C0008', '2023-04-02', '7A'),
('B0009', 'FR2002', 'C0009', '2023-04-03', '8F'),
('B0010', 'FR3001', 'C0010', '2023-04-03', '1D'),
('B0011', 'EI4001', 'C0001', '2023-04-03', '2F'),
('B0012', 'FR4001', 'C0002', '2023-04-03', '3B'),
('B0013', 'FR4002', 'C0003', '2023-04-04', '5C'),
('B0014', 'EI5001', 'C0004', '2023-04-04', '1A'),
('B0015', 'U2001', 'C0005', '2023-04-04', '9F'),
('B0016', 'KL5001', 'C0006', '2023-04-04', '4A'),
('B0017', 'EI6001', 'C0007', '2023-04-05', '6D'),
('B0018', 'BA1001', 'C0008', '2023-04-05', '3A'),
('B0019', 'U2002', 'C0009', '2023-04-05', '7F'),
('B0020', 'BA1002', 'C0010', '2023-04-06', '8D'),
('B0021', 'FR5001', 'C0001', '2023-04-06', '2A');

INSERT INTO payment (payment_number, booking_number, amount_paid, payment_date, method) VALUES
('P0001', 'B0001', 100.50, '2023-03-30', 'Credit Card'),
('P0002', 'B0002', 200.25, '2023-03-30', 'Debit Card'),
('P0003', 'B0003', 120.75, '2023-03-30', 'Credit Card'),
('P0004', 'B0004', 350.00, '2023-03-30', 'PayPal'),
('P0005', 'B0005', 80.50, '2023-03-31', 'Debit Card'),
('P0006', 'B0006', 90.25, '2023-03-31', 'Credit Card'),
('P0007', 'B0007', 175.00, '2023-03-31', 'PayPal'),
('P0008', 'B0008', 250.75, '2023-03-31', 'Credit Card'),
('P0009', 'B0009', 70.00, '2023-04-01', 'Debit Card'),
('P0010', 'B0010', 95.50, '2023-04-01', 'Credit Card'),
('P0011', 'B0011', 180.25, '2023-04-01', 'PayPal'),
('P0012', 'B0012', 110.75, '2023-04-02', 'Credit Card'),
('P0013', 'B0013', 150.00, '2023-04-02', 'Credit Card'),
('P0014', 'B0014', 105.25, '2023-04-02', 'Debit Card'),
('P0015', 'B0015', 65.00, '2023-04-03', 'Credit Card'),
('P0016', 'B0016', 225.50, '2023-04-03', 'PayPal'),
('P0017', 'B0017', 75.00, '2023-04-03', 'Debit Card'),
('P0018', 'B0018', 225.75, '2023-04-04', 'Credit Card'),
('P0019', 'B0019', 125.50, '2023-04-04', 'Credit Card'),
('P0020', 'B0020', 275.00, '2023-04-04', 'PayPal');