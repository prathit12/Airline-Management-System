package Application.JunitTests;

import Application.DAOs.AirportDaoInterface;
import Application.DAOs.BookingDaoInterface;
import Application.DAOs.MySqlAirportDao;
import Application.DAOs.MySqlBookingDao;
import Application.DTOs.Airport;
import Application.DTOs.Booking;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlBookingDaoTest {
    @Test
    void testFindAllBookings() {
        BookingDaoInterface bookingDao = new MySqlBookingDao();

        try{
            List<Booking> bookings = bookingDao.findAllBookings();
            //this is a test to see if the list is empty
            assertNotNull(bookings);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(bookings.size() > 0);
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this is a test to see if the booking is found by id
    @Test
    void testFindBookingByNumber(){
        BookingDaoInterface bookingDao = new MySqlBookingDao();
        String bookingNumber = "B0004";
        try{
            Booking booking = bookingDao.findBookingByNumber(bookingNumber);
            //this is a test to see if the booking is found by id
            //if the booking is found by id, then the booking should not be null
            assertNotNull(booking);
            System.out.println(booking);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this is a test to see if the booking is deleted by NUMBER
    @Test
    void testDeleteBookingByNumber(){
        BookingDaoInterface bookingDao = new MySqlBookingDao();
        String bookingNumber = "B0051";
        try{
            boolean deleted = bookingDao.deleteBookingByNumber(bookingNumber);
            //this is a test to see if the booking is deleted by id
            //if the booking is deleted by id, then the booking should be true
            assertTrue(deleted);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this is a test to see if the booking is inserted
    @Test
    void testInsertBooking() {
        BookingDaoInterface bookingDao = new MySqlBookingDao();
        Booking booking = new Booking("B0051", "EI4001", "C0004", "2023-03-21",  "20F");
        try {
            Booking insertedBooking = bookingDao.insertBooking(booking);
            //this is a test to see if the booking is inserted
            //if the booking is inserted, then the booking should not be null
            assertNotNull(insertedBooking);
            System.out.println(insertedBooking);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //find all bookings by customer number
    @Test
    void testFindAllBookingsByCustomerNumber(){
        BookingDaoInterface bookingDao = new MySqlBookingDao();
        String customerNumber = "C0004";
        try{
            List<Booking> bookings = bookingDao.findAllBookingsByCustomerNumber(customerNumber);
            //this is a test to see if the list is empty
            assertNotNull(bookings);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(bookings.size() > 0);
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //find all bookings by flight number
    @Test
    void testFindAllBookingsByFlightNumber(){
        BookingDaoInterface bookingDao = new MySqlBookingDao();
        String flightNumber = "EI4001";
        try{
            List<Booking> bookings = bookingDao.findAllBookingsByFlightNumber(flightNumber);
            //this is a test to see if the list is empty
            assertNotNull(bookings);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(bookings.size() > 0);
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}