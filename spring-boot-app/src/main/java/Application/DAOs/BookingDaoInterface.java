package Application.DAOs;

import Application.DTOs.Booking;
import Application.Exceptions.DaoException;

import java.util.List;
import java.util.TreeSet;

public interface BookingDaoInterface {
    public List<Booking> findAllBookings() throws DaoException;
    public Booking findBookingByNumber(String bookingNumber) throws DaoException;
    public boolean deleteBookingByNumber(String bookingNumber) throws DaoException;
    public Booking insertBooking(Booking booking) throws DaoException;
    public List<Booking> findAllBookingsByCustomerNumber(String customerNumber) throws DaoException;
    public List<Booking> findAllBookingsByFlightNumber(String flightNumber) throws DaoException;
    public TreeSet<String> populateBookingCache() throws DaoException;
}