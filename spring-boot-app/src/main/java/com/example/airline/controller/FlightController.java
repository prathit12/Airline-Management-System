package com.example.airline.controller;

import Application.DTOs.Flight;
import Application.Exceptions.DaoException;
import com.example.airline.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() throws DaoException {
        return flightService.findAllFlights();
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<Flight> getFlight(@PathVariable String flightNumber) throws DaoException {
        Flight flight = flightService.findFlightByNumber(flightNumber);
        return flight != null ? ResponseEntity.ok(flight) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) throws DaoException {
        return flightService.insertFlight(flight);
    }

    @DeleteMapping("/{flightNumber}")
    public ResponseEntity<Void> deleteFlight(@PathVariable String flightNumber) throws DaoException {
        boolean deleted = flightService.deleteFlightByNumber(flightNumber);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
