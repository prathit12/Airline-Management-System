package com.example.airline.service;

import Application.DAOs.AirportDaoInterface;
import Application.DAOs.MySqlAirportDao;
import Application.DTOs.Airport;
import Application.Exceptions.DaoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    private final AirportDaoInterface airportDao = new MySqlAirportDao();

    public AirportService() throws DaoException {
        // Initialize the airport cache on startup
        airportDao.populateAirportCache();
    }

    public List<Airport> findAllAirports() throws DaoException {
        return airportDao.findAllAirports();
    }

    public Airport findAirportByNumber(String airportNumber) throws DaoException {
        return airportDao.findAirportByNumber(airportNumber);
    }

    public Airport insertAirport(Airport airport) throws DaoException {
        return airportDao.insertAirport(airport);
    }

    public Airport updateAirport(Airport airport) throws DaoException {
        return airportDao.updateAirport(airport);
    }

    public boolean deleteAirportByNumber(String airportNumber) throws DaoException {
        return airportDao.deleteAirportByNumber(airportNumber);
    }
}
