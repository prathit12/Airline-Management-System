# Airline Management System

A comprehensive airline management system built with Spring Boot, featuring user authentication, flight booking, customer management, and payment processing capabilities.

## 🚀 Tech Stack

- **Framework**: Spring Boot 3.2.5
- **Language**: Java 23
- **Database**: MySQL
- **ORM**: Spring Data JPA with Hibernate
- **Authentication**: Spring Security with JWT
- **Database Migration**: Flyway
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Frontend**: HTML, CSS, JavaScript

## 📋 Dependencies

### Core Dependencies
- **Spring Boot Starter Web** - REST API and web functionality
- **Spring Boot Starter Data JPA** - Database access layer
- **Spring Boot Starter Security** - Authentication and authorization
- **Spring Boot Starter Thymeleaf** - Server-side templating
- **Spring Boot Starter Validation** - Input validation

### Database
- **MySQL Connector** - Database connectivity
- **Flyway Core & MySQL** - Database migration management

### Authentication & Security
- **JWT (JSON Web Tokens)** - Token-based authentication
  - `jjwt-api` (0.12.3)
  - `jjwt-impl` (0.12.3)
  - `jjwt-jackson` (0.12.3)

### Utilities
- **Jackson Datatype Hibernate6** - JSON serialization for Hibernate entities

## 🏗️ Project Structure

```
src/main/java/
├── Application/                          # Legacy DAO/DTO layer
│   ├── DAOs/                            # Data Access Objects
│   │   ├── *DaoInterface.java           # DAO interfaces
│   │   ├── MySql*Dao.java              # MySQL implementations
│   │   └── HelperConnection.java        # Database connection helper
│   ├── DTOs/                           # Data Transfer Objects
│   │   ├── Airport.java
│   │   ├── Booking.java
│   │   ├── Customer.java
│   │   ├── Flight.java
│   │   └── Payment.java
│   └── Exceptions/
│       └── DaoException.java           # Custom DAO exceptions
│
├── com/example/airline/                 # Spring Boot application
│   ├── config/                         # Configuration classes
│   │   ├── SecurityConfig.java         # Security configuration
│   │   └── JacksonConfig.java          # JSON serialization config
│   │
│   ├── controller/                     # REST Controllers
│   │   ├── AuthController.java         # Authentication endpoints
│   │   ├── UserController.java         # User management
│   │   ├── FlightController.java       # Flight operations
│   │   ├── BookingController.java      # Booking management
│   │   ├── CustomerController.java     # Customer operations
│   │   ├── PaymentController.java      # Payment processing
│   │   ├── AirportController.java      # Airport management
│   │   ├── WebController.java          # Web page controllers
│   │   └── TestController.java         # API testing endpoints
│   │
│   ├── dto/                           # Request/Response DTOs
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── JwtAuthResponse.java
│   │   ├── UserProfileResponse.java
│   │   ├── PasswordResetRequest.java
│   │   ├── ChangePasswordRequest.java
│   │   └── UpdateProfileRequest.java
│   │
│   ├── entity/                        # JPA Entities
│   │   ├── User.java                  # User entity
│   │   ├── Role.java                  # User roles
│   │   ├── Permission.java            # Role permissions
│   │   ├── PasswordResetToken.java    # Password reset tokens
│   │   ├── UserSession.java           # User session tracking
│   │   ├── Customer.java              # Customer entity
│   │   ├── Flight.java                # Flight entity
│   │   ├── Airport.java               # Airport entity
│   │   ├── Booking.java               # Booking entity
│   │   └── Payment.java               # Payment entity
│   │
│   ├── exception/                     # Custom Exceptions
│   │   ├── GlobalExceptionHandler.java
│   │   ├── AuthenticationException.java
│   │   ├── UserNotFoundException.java
│   │   ├── UserAlreadyExistsException.java
│   │   └── TokenExpiredException.java
│   │
│   ├── repository/                    # Spring Data JPA Repositories
│   │   ├── UserRepository.java
│   │   ├── RoleRepository.java
│   │   ├── PermissionRepository.java
│   │   ├── UserSessionRepository.java
│   │   ├── PasswordResetTokenRepository.java
│   │   ├── CustomerRepository.java
│   │   ├── FlightRepository.java
│   │   ├── AirportRepository.java
│   │   ├── BookingRepository.java
│   │   └── PaymentRepository.java
│   │
│   ├── security/                      # Security components
│   │   ├── JwtTokenProvider.java      # JWT token management
│   │   ├── JwtAuthenticationFilter.java # JWT filter
│   │   └── JwtAuthenticationEntryPoint.java # Auth entry point
│   │
│   ├── service/                       # Business Logic Services
│   │   ├── UserService.java           # User management service
│   │   ├── CustomUserDetailsService.java # Spring Security user details
│   │   ├── EmailService.java          # Email functionality
│   │   ├── FlightService.java         # Flight operations
│   │   ├── BookingService.java        # Booking management
│   │   ├── CustomerService.java       # Customer operations
│   │   ├── PaymentService.java        # Payment processing
│   │   └── AirportService.java        # Airport management
│   │
│   └── AirlineManagementSystemApplication.java # Main application class
```

## 🗂️ Resources Structure

```
src/main/resources/
├── db/migration/                       # Flyway database migrations
│   ├── V001__Create_authentication_tables.sql
│   ├── V002__Insert_default_roles_and_permissions.sql
│   ├── V003__Create_default_admin_user.sql
│   ├── V004__Create_legacy_application_tables.sql
│   └── V005__Fix_flight_table_schema.sql
│
├── static/                            # Static web assets
│   ├── css/style.css                  # Application styles
│   ├── js/                           # JavaScript files
│   │   ├── auth.js                   # Authentication handling
│   │   ├── dashboard.js              # Dashboard functionality
│   │   ├── flights.js                # Flight management
│   │   └── airports.js               # Airport management
│   └── test-api.html                 # API testing interface
│
├── templates/                         # Thymeleaf templates
│   ├── index.html                    # Home page
│   ├── login.html                    # Login form
│   ├── register.html                 # Registration form
│   ├── dashboard.html                # Main dashboard
│   ├── menu.html                     # Navigation menu
│   ├── flights.html                  # Flight management
│   ├── airports.html                 # Airport management
│   ├── customers.html                # Customer management
│   ├── bookings.html                 # Booking management
│   └── payments.html                 # Payment management
│
└── application.properties             # Application configuration
```

## 🔧 Configuration

### Database Configuration
- **Database**: MySQL (localhost:3306)
- **Database Name**: `travel_booking_system`
- **Default Credentials**: root/root
- **JPA**: Hibernate with MySQL dialect
- **Migration**: Flyway for database versioning

### Security Configuration
- **Authentication**: JWT-based authentication
- **Token Expiration**: 1 hour (3600000ms)
- **Password Encoding**: BCrypt

### Server Configuration
- **Port**: 8085
- **Context Path**: Default (/)

## 🚀 Getting Started

### Prerequisites
- Java 23
- MySQL 8.0+
- Maven 3.6+

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd spring-boot-app
   ```

2. **Database Setup**
   ```bash
   # Create MySQL database
   mysql -u root -p
   CREATE DATABASE travel_booking_system;
   ```

3. **Run Flyway migrations**
   ```bash
   mvn flyway:migrate
   ```

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the application**
   - Web Interface: http://localhost:8085
   - API Testing: http://localhost:8085/test-api.html

### Default Admin User
- **Username**: admin
- **Password**: admin123
- **Role**: ADMIN

## 📡 API Endpoints

### Authentication
- `POST /auth/login` - User login
- `POST /auth/register` - User registration
- `POST /auth/logout` - User logout

### User Management
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile
- `POST /api/users/change-password` - Change password

### Flight Management
- `GET /api/flights` - Get all flights
- `POST /api/flights` - Create new flight
- `PUT /api/flights/{id}` - Update flight
- `DELETE /api/flights/{id}` - Delete flight

### Booking Management
- `GET /api/bookings` - Get all bookings
- `POST /api/bookings` - Create new booking
- `GET /api/bookings/{id}` - Get booking details

### Customer Management
- `GET /api/customers` - Get all customers
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer

### Payment Management
- `GET /api/payments` - Get all payments
- `POST /api/payments` - Process payment

### Airport Management
- `GET /api/airports` - Get all airports
- `POST /api/airports` - Create new airport

## 🔐 Security Features

- JWT-based authentication
- Role-based access control (RBAC)
- Password encryption (BCrypt)
- Session management
- Password reset functionality
- CSRF protection
- XSS protection

## 🗄️ Database Schema

The application uses Flyway migrations to manage database schema. Key tables include:
- `users` - User accounts
- `roles` - User roles (ADMIN, USER)
- `permissions` - Role permissions
- `user_sessions` - Active user sessions
- `customers` - Customer information
- `flights` - Flight details
- `airports` - Airport information
- `bookings` - Flight bookings
- `payments` - Payment records

## 🏛️ Architecture

This application follows a hybrid architecture combining:
- **Legacy DAO Pattern** (Application package) - Original data access layer
- **Spring Boot Architecture** (com.example.airline package) - Modern Spring-based implementation
- **RESTful API Design** - For frontend-backend communication
- **MVC Pattern** - Model-View-Controller separation
- **Service Layer Pattern** - Business logic abstraction
- **Repository Pattern** - Data access abstraction

## 🛠️ Development

### Running Tests
```bash
mvn test
```

### Database Migration
```bash
# Run migrations
mvn flyway:migrate

# Clean database (use with caution)
mvn flyway:clean
```

### Building for Production
```bash
mvn clean package
java -jar target/spring-boot-app-0.0.1-SNAPSHOT.jar
```
