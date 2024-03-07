package Application.DAOs;

import Application.DTOs.Booking;
import Application.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MySqlBookingDao extends MySqlDao implements BookingDaoInterface {
    private static TreeSet<String> bookingNumbersCache = new TreeSet<>();

    //to get the helper connection
    private HelperConnection helperConnection = new HelperConnection();

    public TreeSet<String> populateBookingCache() throws DaoException{
        bookingNumbersCache.clear();
        try{
            String query = "SELECT booking_number FROM booking";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while(resultSet.next()){
                String bookingNumber = resultSet.getString("booking_number");
                //add the booking number to the cache
                bookingNumbersCache.add(bookingNumber.toLowerCase());
            }
        }catch(SQLException e){
            throw new DaoException("populateBookingCache() " + e.getMessage());
        }
        return bookingNumbersCache;
    }
    @Override
    public List<Booking> findAllBookings() throws DaoException {
        List<Booking> bookings = new ArrayList<>();
        try{
            String query = "SELECT * FROM booking";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while(resultSet.next()){
                int bookingId = resultSet.getInt("booking_id");
                String bookingNumber = resultSet.getString("booking_number");
                String flightNumber = resultSet.getString("flight_number");
                String customerNumber = resultSet.getString("customer_number");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");

                Booking b = new Booking(bookingId, bookingNumber, flightNumber, customerNumber, travelDate, seatNumber);
                bookings.add(b);
            }
        } catch(SQLException e){
            throw new DaoException("findAllBookings() " + e.getMessage());
        }
        return bookings;
    }

    @Override
    public Booking findBookingByNumber(String bookingNumber) throws DaoException {
        Booking b = null;
        //check if the booking number is in the cache in both upper and lower case
        if(!bookingNumbersCache.contains(bookingNumber.toLowerCase()) && !bookingNumbersCache.contains(bookingNumber.toUpperCase())){
            //if it is NOT in the cache, then it is not in the database
            return null;
        }
        try{
            String query = "SELECT * FROM booking WHERE LOWER(booking_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, bookingNumber.toLowerCase());
            if(resultSet.next()){
                int bookingId = resultSet.getInt("booking_id");
                String flightNumber = resultSet.getString("flight_number");
                String customerNumber = resultSet.getString("customer_number");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");

                b = new Booking(bookingId, bookingNumber, flightNumber, customerNumber, travelDate, seatNumber);
            }
        } catch(SQLException e){
            throw new DaoException("findBookingByNumber() " + e.getMessage());
        }
        return b;
    }

    @Override
    public boolean deleteBookingByNumber(String bookingNumber) throws DaoException {
        boolean deleted = false;
        //check if the booking number is in the cache in both upper and lower case
        if(!bookingNumbersCache.contains(bookingNumber.toLowerCase()) && !bookingNumbersCache.contains(bookingNumber.toUpperCase())){
            //if it is NOT in the cache, then it is not in the database
            return false;
        }
        try{
            String query = "DELETE FROM booking WHERE LOWER(booking_number) = ?";
            int result = helperConnection.executeUpdate(query, bookingNumber.toLowerCase());
            if(result == 1){
                deleted = true;
                //remove the booking number from the cache
                bookingNumbersCache.remove(bookingNumber.toLowerCase());
            }
        } catch(SQLException e){
            throw new DaoException("deleteBookingByNumber() " + e.getMessage());
        }
        return deleted;
    }
    @Override
    public Booking insertBooking(Booking booking) throws DaoException {
        Booking b = null;
        try{
            String query = "INSERT INTO booking (booking_number, flight_number, customer_number, travel_date, seat_number) VALUES (?,?,?,?,?)";
            int result = helperConnection.executeUpdate(query, booking.getBooking_number(), booking.getFlight_number(), booking.getCustomer_number(), booking.getTravel_date(), booking.getSeat_number());
            if(result == 1){
                //add the booking number to the cache
                bookingNumbersCache.add(booking.getBooking_number().toLowerCase());
                Booking insertedBooking = findBookingByNumber(booking.getBooking_number());
                b = insertedBooking;
            }
        } catch(SQLException e){
            throw new DaoException("insertBooking() " + e.getMessage());
        }
        return b;
    }

    @Override
    public List<Booking> findAllBookingsByCustomerNumber(String customerNumber) throws DaoException {
        List<Booking> bookings = new ArrayList<>();
        try{
            String query = "SELECT * FROM booking WHERE LOWER(customer_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, customerNumber.toLowerCase());
            while(resultSet.next()){
                int bookingId = resultSet.getInt("booking_id");
                String bookingNumber = resultSet.getString("booking_number");
                String flightNumber = resultSet.getString("flight_number");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");

                Booking b = new Booking(bookingId, bookingNumber, flightNumber, customerNumber, travelDate, seatNumber);
                bookings.add(b);
            }
        } catch(SQLException e){
            throw new DaoException("findAllBookingsByCustomerNumber() " + e.getMessage());
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllBookingsByFlightNumber(String flightNumber) throws DaoException {
        List<Booking> bookings = new ArrayList<>();
        try{
            String query = "SELECT * FROM booking WHERE LOWER(flight_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, flightNumber.toLowerCase());
            while(resultSet.next()){
                int bookingId = resultSet.getInt("booking_id");
                String bookingNumber = resultSet.getString("booking_number");
                String customerNumber = resultSet.getString("customer_number");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");

                Booking b = new Booking(bookingId, bookingNumber, flightNumber, customerNumber, travelDate, seatNumber);
                bookings.add(b);
            }
        } catch(SQLException e){
            throw new DaoException("findAllBookingsByFlightNumber() " + e.getMessage());
        }
        return bookings;
    }
}

