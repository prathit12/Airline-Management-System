package Application.JunitTests;

import Application.DAOs.AirportDaoInterface;
import Application.DAOs.MySqlAirportDao;
import Application.DTOs.Airport;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MySqlAirportDaoTest {

    //test to find all airports
    @Test
    void testFindAllAirports(){
        AirportDaoInterface airportDao = new MySqlAirportDao();
        try{
            List<Airport> airports = airportDao.findAllAirports();
            //this is a test to see if the list is empty
            assertNotNull(airports);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(airports.size() > 0);
            for (Airport a : airports) {
                System.out.println(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to find airport by number
    @Test
    void testFindAirportByNumber(){
        AirportDaoInterface airportDao = new MySqlAirportDao();
        String airportNumber = "AMS";
        try{
            Airport airport = airportDao.findAirportByNumber(airportNumber);
            //this is a test to see if the airport is found by number
            //if the airport is found by number, then the airport should not be null
            assertNotNull(airport);
            System.out.println(airport);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to delete airport by number
    @Test
    void testDeleteAirportByNumber(){
        AirportDaoInterface airportDao = new MySqlAirportDao();
        String airportNumber = "ttt";
        try{
            boolean deleted = airportDao.deleteAirportByNumber(airportNumber);
            //this is a test to see if the airport is deleted by number
            //if the airport is deleted by number, then the airport should be true
            assertTrue(deleted);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to insert airport
    @Test
    void testInsertAirport() {
        AirportDaoInterface airportDao = new MySqlAirportDao();
        Airport airport = new Airport("ttt", "test", "test");
        try {
            Airport insertedAirport = airportDao.insertAirport(airport);
            //this is a test to see if the airport is inserted
            //if the airport is inserted, then the airport should not be null
            assertNotNull(insertedAirport);
            System.out.println(insertedAirport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //to test to get the uniqueAirportLocations
    @Test
    void testFindUniqueAirportLocation(){
        AirportDaoInterface airportDao = new MySqlAirportDao();
        try{
            Set<String> uniqueAirportLocations = airportDao.uniqueAirportLocation();
            //this is a test to see if the list is empty
            assertNotNull(uniqueAirportLocations);
            //if the set is not empty, then the size of the set should be greater than 0 which is true
            assertTrue(uniqueAirportLocations.size() > 0);
            for (String a : uniqueAirportLocations) {
                System.out.println(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}