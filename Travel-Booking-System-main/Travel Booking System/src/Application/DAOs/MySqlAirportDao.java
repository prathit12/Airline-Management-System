package Application.DAOs;

import Application.DAOs.MySqlDao;
import Application.DTOs.Airport;
import Application.Exceptions.DaoException;
import Application.DAOs.CustomerDaoInterface;
import Application.DTOs.Customer;

import java.sql.*;
import java.util.*;

public class MySqlAirportDao extends MySqlDao implements AirportDaoInterface {

    private static TreeSet<String> airportNumberCache = new TreeSet<>();

    HelperConnection helperConnection = new HelperConnection();

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
            throw new DaoException("findAllAirportsresultSet() " + e.getMessage());
        }

        return airports;
    }


    public Airport findAirportByNumber(String airportNumber) throws DaoException {
        Airport a = null;

        //check if the airport number is in the cache in both upper and lower case
        if(!airportNumberCache.contains(airportNumber.toUpperCase()) && !airportNumberCache.contains(airportNumber.toLowerCase())){
            return null;
        }

        try{
            String query = "SELECT * FROM airport WHERE LOWER(airport_number) = LOWER(?)";
            ResultSet resultSet = helperConnection.executeQuery(query, airportNumber);

            if(resultSet.next()){
                int airportId = resultSet.getInt("airport_id");
                String airportName = resultSet.getString("airport_name");
                String airportLocation = resultSet.getString("airport_location");

                a = new Airport(airportId,airportNumber, airportName, airportLocation);
            }
        } catch(SQLException e){
            throw new DaoException("findAirportByNumber() " + e.getMessage());
        }

        return a;
    }


    @Override
    public boolean deleteAirportByNumber(String airportNumber) throws DaoException {
        boolean deleted = false;
        //check if the airport number is in the cache in both upper and lower case
        if(!airportNumberCache.contains(airportNumber.toUpperCase()) && !airportNumberCache.contains(airportNumber.toLowerCase())){
            return false;
        }
        try {
            String query = "DELETE FROM airport WHERE LOWER(airport_number) = LOWER(?)";
            int rowsAffected = helperConnection.executeUpdate(query, airportNumber);

            if (rowsAffected == 1) {
                deleted = true;

                //remove the airport number from the cache
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
                //add the airport number to the cache
                airportNumberCache.add(airport.getAirport_number().toLowerCase());
                // retrieve the inserted airport and return it
                 Airport insertedAirport = findAirportByNumber(airport.getAirport_number());
                 a = insertedAirport;
            } else {
                throw new DaoException("Airport insertion failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("insertAirport() " + e.getMessage());
        }
        return a;
    }

    @Override
    public Set<String> uniqueAirportLocation() throws DaoException {
        //in case can easily change to TreeSet
        HashSet<String> airportLocations = new HashSet<>();

        try {
            String query = "SELECT DISTINCT airport_location FROM airport";
            ResultSet resultSet = helperConnection.executeQuery(query);

            while (resultSet.next()) {
                String airportLocation = resultSet.getString("airport_location");
                airportLocations.add(airportLocation);
            }

        } catch (SQLException e) {
            throw new DaoException("uniqueAirportLocationresultSet() " + e.getMessage());
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
            throw new DaoException("findAirportByLocationresultSet() " + e.getMessage());
        }
        return airports;
    }
}
