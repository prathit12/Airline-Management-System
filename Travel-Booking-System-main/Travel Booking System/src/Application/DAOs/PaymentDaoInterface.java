package Application.DAOs;

import Application.DTOs.Payment;
import Application.DAOs.MySqlDao;
import Application.Exceptions.DaoException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface PaymentDaoInterface {
    //Feature 1 – Find all Entities e.g. findAllPlayers() to return a List of all the entities and display
    //that list.
    public List<Payment> findAllPayments() throws DaoException;
    //Feature 2 – Find and Display (a single) Entity by Key e.g. findPlayerById( id ) – return a single
    //entity and display its contents.
    public Payment findPaymentByNumber(String paymentNumber) throws DaoException;
    //Feature 3 – Delete an Entity by key e.g. deletePlayerById( id ) – remove entity from database
    public boolean deletePaymentByNumber(String paymentNumber) throws DaoException;
    //Feature 4 – Insert an Entity in the database using DAO (gather data from user, store in DTO
    //object, pass into method insertPlayer ( Player ), return new entity including assigned auto-id.
    public Payment insertPayment(Payment payment) throws DaoException;

    //an extra method mae by me to find all payments by booking number so that when a booking is
    //about to be deleted, all their affected payments will be displayed
    public List<Payment> findAllPaymentsByBookingNumber(String bookingNumber) throws DaoException;

    //to store the unique payment type using a set that i will use in the App to filter the payments, return a set
    public Set<String> uniquePaymentMethod() throws DaoException;

    //to find payment by payment type
    public List<Payment> findPaymentByPaymentMethod(String paymentMethod) throws DaoException;

    public TreeSet<String> populatePaymentCache() throws DaoException;

}
