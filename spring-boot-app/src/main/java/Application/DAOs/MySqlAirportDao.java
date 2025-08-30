package Application.DAOs;

import Application.DTOs.Airport;
import Application.Exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MySqlAirportDao extends MySqlDao implements AirportDaoInterface {
    private static final TreeSet<String> airportNumberCache = new TreeSet<>();
    private final HelperConnection helperConnection = new HelperConnection();

    @Override
    public TreeSet<String> populateAirportCache() throws DaoException {
        airportNumberCache.clear();
        try {
            String query = "SELECT airport_number FROM airport";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                String airportNumber = resultSet.getString("airport_number");
                airportNumberCache.add(airportNumber.toLowerCase());
            }
        } catch (SQLException e) {
            throw new DaoException("populateAirportCache() " + e.getMessage());
        }
        return airportNumberCache;
    }

    @Override
    public List<Airport> findAllAirports() throws DaoException {
        List<Airport> airports = new ArrayList<>();
        try {
            String query = "SELECT * FROM airport";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                int airportId = resultSet.getInt("airport_id");
                String airportNumber = resultSet.getString("airport_number");
                String airportName = resultSet.getString("airport_name");
                String airportLocation = resultSet.getString("airport_location");
                Airport a = new Airport(airportId, airportNumber, airportName, airportLocation);
                airports.add(a);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllAirports() " + e.getMessage());
        }
        return airports;
    }

    @Override
    public Airport findAirportByNumber(String airportNumber) throws DaoException {
        Airport a = null;
        if (!airportNumberCache.contains(airportNumber.toUpperCase()) && !airportNumberCache.contains(airportNumber.toLowerCase())) {
            return null;
        }
        try {
            String query = "SELECT * FROM airport WHERE LOWER(airport_number) = LOWER(?)";
            ResultSet resultSet = helperConnection.executeQuery(query, airportNumber);
            if (resultSet.next()) {
                int airportId = resultSet.getInt("airport_id");
                String airportName = resultSet.getString("airport_name");
                String airportLocation = resultSet.getString("airport_location");
                a = new Airport(airportId, airportNumber, airportName, airportLocation);
            }
        } catch (SQLException e) {
            throw new DaoException("findAirportByNumber() " + e.getMessage());
        }
        return a;
    }

    @Override
    public boolean deleteAirportByNumber(String airportNumber) throws DaoException {
        boolean deleted = false;
        if (!airportNumberCache.contains(airportNumber.toUpperCase()) && !airportNumberCache.contains(airportNumber.toLowerCase())) {
            return false;
        }
        try {
            String query = "DELETE FROM airport WHERE LOWER(airport_number) = LOWER(?)";
            int rowsAffected = helperConnection.executeUpdate(query, airportNumber);
            if (rowsAffected == 1) {
                deleted = true;
                airportNumberCache.remove(airportNumber.toLowerCase());
            }
        } catch (SQLException e) {
            throw new DaoException("deleteAirportByNumber() " + e.getMessage());
        }
        return deleted;
    }

    @Override
    public Airport insertAirport(Airport airport) throws DaoException {
        Airport a = null;
        try {
            String query = "INSERT INTO airport (airport_number, airport_name, airport_location) VALUES (?,?,?)";
            int rowsAffected = helperConnection.executeUpdate(query, airport.getAirport_number(), airport.getAirport_name(), airport.getAirport_location());
            if (rowsAffected == 1) {
                airportNumberCache.add(airport.getAirport_number().toLowerCase());
                a = findAirportByNumber(airport.getAirport_number());
            } else {
                throw new DaoException("Airport insertion failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("insertAirport() " + e.getMessage());
        }
        return a;
    }

    @Override
    public Airport updateAirport(Airport airport) throws DaoException {
        try {
            String query = "UPDATE airport SET airport_name = ?, airport_location = ? WHERE airport_number = ?";
            int rowsAffected = helperConnection.executeUpdate(query, airport.getAirport_name(), airport.getAirport_location(), airport.getAirport_number());
            if (rowsAffected == 1) {
                return findAirportByNumber(airport.getAirport_number());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("updateAirport() " + e.getMessage());
        }
    }

    @Override
    public Set<String> uniqueAirportLocation() throws DaoException {
        Set<String> airportLocations = new HashSet<>();
        try {
            String query = "SELECT DISTINCT airport_location FROM airport";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                String airportLocation = resultSet.getString("airport_location");
                airportLocations.add(airportLocation);
            }
        } catch (SQLException e) {
            throw new DaoException("uniqueAirportLocation() " + e.getMessage());
        }
        return airportLocations;
    }

    @Override
    public List<Airport> findAirportByLocation(String airportLocation) throws DaoException {
        List<Airport> airports = new ArrayList<>();
        try {
            String query = "SELECT * FROM airport WHERE LOWER(airport_location) = LOWER(?)";
            ResultSet resultSet = helperConnection.executeQuery(query, airportLocation);
            while (resultSet.next()) {
                int airportId = resultSet.getInt("airport_id");
                String airportNumber = resultSet.getString("airport_number");
                String airportName = resultSet.getString("airport_name");
                Airport a = new Airport(airportId, airportNumber, airportName, airportLocation);
                airports.add(a);
            }
        } catch (SQLException e) {
            throw new DaoException("findAirportByLocation() " + e.getMessage());
        }
        return airports;
    }
}
