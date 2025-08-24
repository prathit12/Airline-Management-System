# Airline Management System

This repository contains the original Travel Booking System alongside a Spring Boot application located in `spring-boot-app`.

The Spring Boot module reuses the existing DAO and DTO packages to expose REST services modeled after the legacy client/server architecture.

## Flight endpoints

- `GET /flights` – list all flights
- `GET /flights/{flightNumber}` – retrieve a flight by number
- `POST /flights` – insert a flight (JSON body matching `Application.DTOs.Flight`)
- `DELETE /flights/{flightNumber}` – delete a flight by number

## Airport endpoints

- `GET /airports` – list all airports
- `GET /airports/{airportNumber}` – retrieve an airport by number
- `POST /airports` – insert an airport (JSON body matching `Application.DTOs.Airport`)
- `DELETE /airports/{airportNumber}` – delete an airport by number

## Building

```
cd spring-boot-app
mvn clean package
```

## Running

```
cd spring-boot-app
mvn spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080).

Database connection settings can be adjusted in `spring-boot-app/src/main/resources/application.properties`.
