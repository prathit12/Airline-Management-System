# Travel-Booking-System

## Project Description
This airline management system is a Java-based application which facilitates the handling and management of data pertinent to an airline business. The major entities include Customers, Airports, Flights, Bookings, and Payments. Each entity comes with features to insert, delete, and find entities by their respective IDs. The application also validates data and handles foreign key constraints, ensuring the integrity of the database.

## Installation 
### Pre-requisites:

* Java Development Kit (JDK) 8 or higher
* MySQL Server
* JDBC Driver

### Steps:

* Import the project into your favorite Java IDE.
* Set up a local MySQL server and create a database named travel_booking_system.
* Run the newSqlSetUp.sql script in your MySQL server to set up the required tables and relationships.

## Usage
The application comes with a user-friendly text-based menu that facilitates interaction with the various features.

* Customer Management: Allows you to add, delete, find (by ID), and display all customers.
* Airport Management: Manage airports with the same functionality as Customer Management.
* Flight Management: Manage flights with additional features including setting departure and arrival times.
* Booking Management: Manage bookings with a feature that validates seat numbers and booking dates.
* Payment Management: Handle payments and validate payment amounts and dates.

## Testing
JUnit tests have been written for most of the application's features. They can be found under the 'JunitTests' directory in the project structure. You can run these tests through your IDE.

## Features
* Data Management: Facilitates management of all airline-related entities.
* CRUD Operations: Enables insertion, deletion, and finding entities (by ID and all entities).
* Data Validation: All data inputs are validated before they're stored in the database, preventing invalid data entry.
* Handling Foreign Key Constraints: The application ensures data integrity by preventing deletion of entities linked to others.
* Filtering System: Users can filter data based on a variety of attributes, enhancing the search functionality.

## Contributing
Contributions are welcome! Please fork the project and create a pull request with your changes.
