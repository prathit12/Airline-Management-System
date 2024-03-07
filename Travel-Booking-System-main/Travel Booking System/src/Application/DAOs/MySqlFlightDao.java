package Application.DAOs;

import Application.DTOs.Flight;
import Application.Exceptions.DaoException;
import Application.DAOs.MySqlDao;
import Application.DAOs.FlightDaoInterface;

import java.sql.*;
import java.util.*;

public class MySqlFlightDao extends MySqlDao implements FlightDaoInterface {

    private static TreeSet<String> flightNumbersCache = new TreeSet<>();

    //to get the helper connection
    private HelperConnection helperConnection = new HelperConnection();

    public TreeSet<String> populateFlightCache() throws DaoException{
        flightNumbersCache.clear();
        try{
            String query = "SELECT flight_number FROM flight";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while(resultSet.next()){
                String flightNumber = resultSet.getString("flight_number");
                //add the flight number to the cache
                flightNumbersCache.add(flightNumber.toLowerCase());
            }
        }catch(SQLException e){
            throw new DaoException("populateFlightCache() " + e.getMessage());
        }
        return flightNumbersCache;
    }
    @Override
    public List<Flight> findAllFlights() throws DaoException {
        List<Flight> flights = new ArrayList<>();
        try {
            String query = "SELECT * FROM flight";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String airportNumber = resultSet.getString("airport_number");
                String departureLocation = resultSet.getString("departure_location");
                String departureTime = resultSet.getString("departure_time");
                String arrivalLocation = resultSet.getString("arrival_location");
                String arrivalTime = resultSet.getString("arrival_time");
                String airlineName = resultSet.getString("airline_name");
                double flightCost = resultSet.getDouble("flight_cost");

                Flight flight = new Flight(flightId, flightNumber, airportNumber, departureLocation, departureTime, arrivalLocation, arrivalTime, airlineName, flightCost);
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllFlights() " + e.getMessage());
        }
        return flights;
    }

    @Override
    public Flight findFlightByNumber(String flightNumber) throws DaoException {
        Flight flight = null;
        //check if the flight number is in the cache in both upper and lower case
        if(!flightNumbersCache.contains(flightNumber.toUpperCase()) && !flightNumbersCache.contains(flightNumber.toLowerCase())){
            //if it is, then we can skip the database query
            return null;
        }
        try {
            String query = "SELECT * FROM flight WHERE LOWER(flight_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, flightNumber.toLowerCase());
            if (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String airportNumber = resultSet.getString("airport_number");
                String departureLocation = resultSet.getString("departure_location");
                String departureTime = resultSet.getString("departure_time");
                String arrivalLocation = resultSet.getString("arrival_location");
                String arrivalTime = resultSet.getString("arrival_time");
                String airlineName = resultSet.getString("airline_name");
                double flightCost = resultSet.getDouble("flight_cost");

                flight = new Flight(flightId, flightNumber, airportNumber, departureLocation, departureTime, arrivalLocation, arrivalTime, airlineName, flightCost);
            }
        } catch (SQLException e) {
            throw new DaoException("findFlightByNumber() " + e.getMessage());
        }
        return flight;
    }

    @Override
    public boolean deleteFlightByNumber(String flightNumber) throws DaoException {
        boolean deleted = false;
        //check if the flight number is in the cache in both upper and lower case
        if(!flightNumbersCache.contains(flightNumber.toUpperCase()) && !flightNumbersCache.contains(flightNumber.toLowerCase())){
            //if it is, then we can skip the database query
            return false;
        }
        try {
            String query = "DELETE FROM flight WHERE LOWER(flight_number) = ?";
            int result = helperConnection.executeUpdate(query, flightNumber.toLowerCase());
            if (result == 1) {
                deleted = true;
                //remove the flight number from the cache
                flightNumbersCache.remove(flightNumber.toLowerCase());
            }
        } catch (SQLException e) {
            throw new DaoException("deleteFlightByNumber() " + e.getMessage());
        }
        return deleted;
    }

    @Override
    public Flight insertFlight(Flight flight) throws DaoException {
        Flight f = null;
        try {
            String query = "INSERT INTO flight (flight_number, airport_number, departure_location, departure_time, arrival_location, arrival_time, airline_name, flight_cost) VALUES (?,?,?,?,?,?,?,?)";
            int result = helperConnection.executeUpdate(query, flight.getFlight_number(), flight.getAirport_number(), flight.getDeparture_location(), flight.getDeparture_time(), flight.getArrival_location(), flight.getArrival_time(), flight.getAirline_name(), flight.getFlight_cost());
            if (result == 1) {
                //add the flight number to the cache
                flightNumbersCache.add(flight.getFlight_number().toLowerCase());
                Flight insertedFlight = findFlightByNumber(flight.getFlight_number());
                f = insertedFlight;
            }
        } catch (SQLException e) {
            throw new DaoException("insertFlight() " + e.getMessage());
        }
        return f;
    }

    @Override
    public List<Flight> findAllFlightsByAirportNumber(String airportNumber) throws DaoException {
        List<Flight> flights = new ArrayList<>();
        try {
            String query = "SELECT * FROM flight WHERE LOWER(airport_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, airportNumber.toLowerCase());
            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String departureLocation = resultSet.getString("departure_location");
                String departureTime = resultSet.getString("departure_time");
                String arrivalLocation = resultSet.getString("arrival_location");
                String arrivalTime = resultSet.getString("arrival_time");
                String airlineName = resultSet.getString("airline_name");
                double flightCost = resultSet.getDouble("flight_cost");

                Flight flight = new Flight(flightId, flightNumber, airportNumber, departureLocation, departureTime, arrivalLocation, arrivalTime, airlineName, flightCost);
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllFlightsByAirportNumber() " + e.getMessage());
        }
        return flights;
    }

    @Override
    public Set<String> uniqueAirlineName() throws DaoException {
        Set<String> airlineNames = new HashSet<>();
        try {
            String query = "SELECT DISTINCT airline_name FROM flight";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                String airlineName = resultSet.getString("airline_name");
                airlineNames.add(airlineName);
            }
        } catch (SQLException e) {
            throw new DaoException("uniqueAirlineName() " + e.getMessage());
        }
        return airlineNames;
    }

    @Override
    public List<Flight> findFlightByAirlineName(String airlineName) throws DaoException {
        List<Flight> flights = new ArrayList<>();
        try {
            String query = "SELECT * FROM flight WHERE LOWER(airline_name) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, airlineName.toLowerCase());
            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String airportNumber = resultSet.getString("airport_number");
                String departureLocation = resultSet.getString("departure_location");
                String departureTime = resultSet.getString("departure_time");
                String arrivalLocation = resultSet.getString("arrival_location");
                String arrivalTime = resultSet.getString("arrival_time");
                double flightCost = resultSet.getDouble("flight_cost");

                Flight flight = new Flight(flightId, flightNumber, airportNumber, departureLocation, departureTime, arrivalLocation, arrivalTime, airlineName, flightCost);
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DaoException("findFlightByAirlineName() " + e.getMessage());
        }
        return flights;
    }

    //to store the deparure time in morning, afternoon and night using a map that i will use in the App to filter the flights, return a map
    public Map<String, List<Flight>> timeOfFlight() throws DaoException {
        //check the departure time if it is between 00:00 and 11:59 then it is morning, 12:00 and 17:59 is afternoon and 18:00 and 23:59 is night
        Map<String, List<Flight>> timeOfFlight = new HashMap<>();
        List<Flight> morningFlights = new ArrayList<>();
        List<Flight> afternoonFlights = new ArrayList<>();
        List<Flight> nightFlights = new ArrayList<>();

        try {
            String query = "SELECT * FROM flight";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String airportNumber = resultSet.getString("airport_number");
                String departureLocation = resultSet.getString("departure_location");
                String departureTime = resultSet.getString("departure_time");
                String arrivalLocation = resultSet.getString("arrival_location");
                String arrivalTime = resultSet.getString("arrival_time");
                String airlineName = resultSet.getString("airline_name");
                double flightCost = resultSet.getDouble("flight_cost");

                Flight flight = new Flight(flightId, flightNumber, airportNumber, departureLocation, departureTime, arrivalLocation, arrivalTime, airlineName, flightCost);

                //check the departure time if it is between 00:00 and 11:59 then it is morning, 12:00 and 17:59 is afternoon and 18:00 and 23:59 is night
                //using the compareTo method of the String class
                if (departureTime.compareTo("00:00:00") >= 0 && departureTime.compareTo("11:59:59") <= 0) {
                    morningFlights.add(flight);
                } else if (departureTime.compareTo("12:00:00") >= 0 && departureTime.compareTo("17:59:59") <= 0) {
                    afternoonFlights.add(flight);
                } else if (departureTime.compareTo("18:00:00") >= 0 && departureTime.compareTo("23:59:59") <= 0) {
                    nightFlights.add(flight);
                }
            }
            timeOfFlight.put("morning", morningFlights);
            timeOfFlight.put("afternoon", afternoonFlights);
            timeOfFlight.put("night", nightFlights);
        } catch (SQLException e) {
            throw new DaoException("timeOfFlight() " + e.getMessage());
        }
        return timeOfFlight;
    }
}
