package Application.DAOs;

import Application.DTOs.Payment;
import Application.Exceptions.DaoException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface PaymentDaoInterface {
    public List<Payment> findAllPayments() throws DaoException;
    public Payment findPaymentByNumber(String paymentNumber) throws DaoException;
    public boolean deletePaymentByNumber(String paymentNumber) throws DaoException;
    public Payment insertPayment(Payment payment) throws DaoException;
    public List<Payment> findAllPaymentsByBookingNumber(String bookingNumber) throws DaoException;
    public Set<String> uniquePaymentMethod() throws DaoException;
    public List<Payment> findPaymentByPaymentMethod(String paymentMethod) throws DaoException;
    public TreeSet<String> populatePaymentCache() throws DaoException;
}