package Application.JunitTests;


import Application.DAOs.FlightDaoInterface;
import Application.DAOs.MySqlFlightDao;
import Application.DTOs.Flight;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlFlightDaoTest {

    //test to find all flights
    @Test
    void testFindAllFlights(){
        FlightDaoInterface flightDao = new MySqlFlightDao();
        try{
            List<Flight> flights = flightDao.findAllFlights();
            //this is a test to see if the list is empty
            assertNotNull(flights);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(flights.size() > 0);
            for (Flight f : flights) {
                System.out.println(f);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to find flight by number
    @Test
    void testFindFlightByNumber(){
        FlightDaoInterface flightDao = new MySqlFlightDao();
        String flightNumber = "ba6001";
        try{
            Flight flight = flightDao.findFlightByNumber(flightNumber);
            //this is a test to see if the flight is found by number
            //if the flight is found by number, then the flight should not be null
            assertNotNull(flight);
            System.out.println(flight);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to delete flight by number
    @Test
    void testDeleteFlightByNumber(){
        FlightDaoInterface flightDao = new MySqlFlightDao();
        String flightNumber = "kktest";
        try{
            boolean deleted = flightDao.deleteFlightByNumber(flightNumber);
            //this is a test to see if the flight is deleted by number
            //if the flight is deleted by number, then the flight should be true
            assertTrue(deleted);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to insert flight
    @Test
    void testInsertFlight() {
        FlightDaoInterface flightDao = new MySqlFlightDao();
        Flight flight = new Flight("kktest", "ORY", "AMS","16:50:00", "germany","20:00:00", "pass", 100);
        try {
            Flight insertedFlight = flightDao.insertFlight(flight);
            //this is a test to see if the flight is inserted
            //if the flight is inserted, then the flight should not be null
            assertNotNull(insertedFlight);
            System.out.println(insertedFlight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //find all flight by airport number
    @Test
    void testFindAllFlightsByAirportNumber(){
        FlightDaoInterface flightDao = new MySqlFlightDao();
        String airportNumber = "ORY";
        try{
            List<Flight> flights = flightDao.findAllFlightsByAirportNumber(airportNumber);
            //this is a test to see if the list is empty
            assertNotNull(flights);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(flights.size() > 0);
            for (Flight f : flights) {
                System.out.println(f);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}