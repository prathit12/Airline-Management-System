package Application.DAOs;

import Application.DTOs.Booking;
import Application.DAOs.MySqlDao;
import Application.Exceptions.DaoException;

import java.util.List;
import java.util.TreeSet;

public interface BookingDaoInterface {
    //Feature 1 – Find all Entities e.g. findAllPlayers() to return a List of all the entities and display
    //that list.
    public List<Booking> findAllBookings() throws DaoException;
    //Feature 2 – Find and Display (a single) Entity by Key e.g. findPlayerById( id ) – return a single
    //entity and display its contents.
    public Booking findBookingByNumber(String bookingNumber) throws DaoException;
    //Feature 3 – Delete an Entity by key e.g. deletePlayerById( id ) – remove entity from database
    public boolean deleteBookingByNumber(String bookingNumber) throws DaoException;
    //Feature 4 – Insert an Entity in the database using DAO (gather data from user, store in DTO
    //object, pass into method insertPlayer ( Player ), return new entity including assigned auto-id.
    public Booking insertBooking(Booking booking) throws DaoException;

    //an extra method mae by me to find all bookings by customer number so that when a customer is
    //about to be deleted, all their baffected bookings will be displayed
    public List<Booking> findAllBookingsByCustomerNumber(String customerNumber) throws DaoException;

    //an extra method made by me to find all bookings by flight number so that when a flight is
    //about to be deleted, all their affected bookings will be displayed
    public List<Booking> findAllBookingsByFlightNumber(String flightNumber) throws DaoException;

    public TreeSet<String> populateBookingCache() throws DaoException;

}
