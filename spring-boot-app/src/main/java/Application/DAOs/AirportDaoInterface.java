package Application.DAOs;

import Application.DTOs.Airport;
import Application.Exceptions.DaoException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface AirportDaoInterface {
    List<Airport> findAllAirports() throws DaoException;

    Airport findAirportByNumber(String airportNumber) throws DaoException;

    boolean deleteAirportByNumber(String airportNumber) throws DaoException;

    Airport insertAirport(Airport airport) throws DaoException;

    Set<String> uniqueAirportLocation() throws DaoException;

    List<Airport> findAirportByLocation(String airportLocation) throws DaoException;

    TreeSet<String> populateAirportCache() throws DaoException;
}
