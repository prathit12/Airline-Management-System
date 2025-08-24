package com.example.airline.service;

import Application.DAOs.FlightDaoInterface;
import Application.DAOs.MySqlFlightDao;
import Application.DTOs.Flight;
import Application.Exceptions.DaoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    private final FlightDaoInterface flightDao = new MySqlFlightDao();

    public List<Flight> findAllFlights() throws DaoException {
        return flightDao.findAllFlights();
    }

    public Flight findFlightByNumber(String flightNumber) throws DaoException {
        return flightDao.findFlightByNumber(flightNumber);
    }

    public Flight insertFlight(Flight flight) throws DaoException {
        return flightDao.insertFlight(flight);
    }

    public boolean deleteFlightByNumber(String flightNumber) throws DaoException {
        return flightDao.deleteFlightByNumber(flightNumber);
    }
}
