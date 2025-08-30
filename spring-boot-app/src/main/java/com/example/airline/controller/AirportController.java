package com.example.airline.controller;

import Application.DTOs.Airport;
import Application.Exceptions.DaoException;
import com.example.airline.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public List<Airport> getAllAirports() throws DaoException {
        return airportService.findAllAirports();
    }

    @GetMapping("/{airportNumber}")
    public ResponseEntity<Airport> getAirport(@PathVariable String airportNumber) throws DaoException {
        Airport airport = airportService.findAirportByNumber(airportNumber);
        return airport != null ? ResponseEntity.ok(airport) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport airport) throws DaoException {
        return airportService.insertAirport(airport);
    }

    @PutMapping("/{airportNumber}")
    public ResponseEntity<Airport> updateAirport(@PathVariable String airportNumber, @RequestBody Airport airport) throws DaoException {
        airport.setAirport_number(airportNumber); // Ensure the airport number matches the path
        Airport updatedAirport = airportService.updateAirport(airport);
        return updatedAirport != null ? ResponseEntity.ok(updatedAirport) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{airportNumber}")
    public ResponseEntity<Void> deleteAirport(@PathVariable String airportNumber) throws DaoException {
        boolean deleted = airportService.deleteAirportByNumber(airportNumber);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
