package Application.DAOs;

import Application.DTOs.CustomerBooking;
import Application.Exceptions.DaoException;

import java.util.List;

public interface CustomerBookingDaoInterface {
    public List<CustomerBooking> findAllCustomerBookings() throws DaoException;

}
