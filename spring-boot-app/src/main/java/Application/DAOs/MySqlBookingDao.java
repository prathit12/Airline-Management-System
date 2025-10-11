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
                int customerId = resultSet.getInt("customer_id");
                int flightId = resultSet.getInt("flight_id");
                String passengerName = resultSet.getString("passenger_name");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");
                java.math.BigDecimal totalAmount = resultSet.getBigDecimal("total_amount");
                String bookingStatus = resultSet.getString("booking_status");

                Booking b = new Booking(bookingId, bookingNumber, customerId, flightId, passengerName, travelDate, seatNumber, totalAmount, bookingStatus);
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
                int customerId = resultSet.getInt("customer_id");
                int flightId = resultSet.getInt("flight_id");
                String passengerName = resultSet.getString("passenger_name");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");
                java.math.BigDecimal totalAmount = resultSet.getBigDecimal("total_amount");
                String bookingStatus = resultSet.getString("booking_status");

                b = new Booking(bookingId, bookingNumber, customerId, flightId, passengerName, travelDate, seatNumber, totalAmount, bookingStatus);
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
            String query = "INSERT INTO booking (booking_number, customer_id, flight_id, passenger_name, travel_date, seat_number, total_amount, booking_status) VALUES (?,?,?,?,?,?,?,?)";
            int result = helperConnection.executeUpdate(query, booking.getBooking_number(), booking.getCustomer_id(), booking.getFlight_id(), booking.getPassenger_name(), booking.getTravel_date(), booking.getSeat_number(), booking.getTotal_amount(), booking.getBooking_status());
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
            String query = "SELECT b.*, c.customer_number FROM booking b JOIN customer c ON b.customer_id = c.customer_id WHERE LOWER(c.customer_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, customerNumber.toLowerCase());
            while(resultSet.next()){
                int bookingId = resultSet.getInt("booking_id");
                String bookingNumber = resultSet.getString("booking_number");
                int customerId = resultSet.getInt("customer_id");
                int flightId = resultSet.getInt("flight_id");
                String passengerName = resultSet.getString("passenger_name");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");
                java.math.BigDecimal totalAmount = resultSet.getBigDecimal("total_amount");
                String bookingStatus = resultSet.getString("booking_status");

                Booking b = new Booking(bookingId, bookingNumber, customerId, flightId, passengerName, travelDate, seatNumber, totalAmount, bookingStatus);
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
            String query = "SELECT b.*, f.flight_number FROM booking b JOIN flight f ON b.flight_id = f.flight_id WHERE LOWER(f.flight_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, flightNumber.toLowerCase());
            while(resultSet.next()){
                int bookingId = resultSet.getInt("booking_id");
                String bookingNumber = resultSet.getString("booking_number");
                int customerId = resultSet.getInt("customer_id");
                int flightId = resultSet.getInt("flight_id");
                String passengerName = resultSet.getString("passenger_name");
                String travelDate = resultSet.getString("travel_date");
                String seatNumber = resultSet.getString("seat_number");
                java.math.BigDecimal totalAmount = resultSet.getBigDecimal("total_amount");
                String bookingStatus = resultSet.getString("booking_status");

                Booking b = new Booking(bookingId, bookingNumber, customerId, flightId, passengerName, travelDate, seatNumber, totalAmount, bookingStatus);
                bookings.add(b);
            }
        } catch(SQLException e){
            throw new DaoException("findAllBookingsByFlightNumber() " + e.getMessage());
        }
        return bookings;
    }
}