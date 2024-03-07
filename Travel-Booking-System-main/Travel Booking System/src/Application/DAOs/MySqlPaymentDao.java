package Application.DAOs;

import Application.DTOs.Payment;
import Application.DAOs.MySqlDao;
import Application.Exceptions.DaoException;

import java.sql.*;
import java.util.*;

public class MySqlPaymentDao extends MySqlDao implements PaymentDaoInterface {
    private static TreeSet<String> paymentNumbersCache = new TreeSet<>();

    //to get the helper connection
    private HelperConnection helperConnection = new HelperConnection();

    public TreeSet<String> populatePaymentCache() throws DaoException {
        paymentNumbersCache.clear();
        try {
            String query = "SELECT payment_number FROM payment";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                String paymentNumber = resultSet.getString("payment_number");
                //add the payment number to the cache
                paymentNumbersCache.add(paymentNumber.toLowerCase());
            }
        } catch (SQLException e) {
            throw new DaoException("populatePaymentCache() " + e.getMessage());
        }
        return paymentNumbersCache;
    }

    @Override
    public List<Payment> findAllPayments() throws DaoException {
        List<Payment> payments = new ArrayList<>();
        try {
            String query = "SELECT * FROM payment";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                String paymentNumber = resultSet.getString("payment_number");
                String bookingNumber = resultSet.getString("booking_number");
                double amountPaid = resultSet.getDouble("amount_paid");
                String paymentDate = resultSet.getString("payment_date");
                String method = resultSet.getString("method");

                Payment p = new Payment(paymentId, paymentNumber, bookingNumber, amountPaid, paymentDate, method);
                payments.add(p);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllPayments() " + e.getMessage());
        }
        return payments;
    }

    @Override
    public Payment findPaymentByNumber(String paymentNumber) throws DaoException {
        Payment p = null;
        //check if the payment number is in the cache in both upper and lower case
        if (!paymentNumbersCache.contains(paymentNumber.toLowerCase()) && !paymentNumbersCache.contains(paymentNumber.toUpperCase())) {
            return null;
        }
        try {
            String query = "SELECT * FROM payment WHERE LOWER(payment_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, paymentNumber.toLowerCase());
            if (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                String bookingNumber = resultSet.getString("booking_number");
                double amountPaid = resultSet.getDouble("amount_paid");
                String paymentDate = resultSet.getString("payment_date");
                String method = resultSet.getString("method");

                p = new Payment(paymentId, paymentNumber, bookingNumber, amountPaid, paymentDate, method);
            }
        } catch (SQLException e) {
            throw new DaoException("findPaymentByNumber() " + e.getMessage());
        }
        return p;
    }

    @Override
    public boolean deletePaymentByNumber(String paymentNumber) throws DaoException {
        boolean deleted = false;
        //check if the payment number is in the cache in both upper and lower case
        if (!paymentNumbersCache.contains(paymentNumber.toLowerCase()) && !paymentNumbersCache.contains(paymentNumber.toUpperCase())) {
            return false;
        }
        try {
            String query = "DELETE FROM payment WHERE LOWER(payment_number) = ?";
            int result = helperConnection.executeUpdate(query, paymentNumber.toLowerCase());
            if (result == 1) {
                deleted = true;
                //remove the payment number from the cache
                paymentNumbersCache.remove(paymentNumber.toLowerCase());
            }
        } catch (SQLException e) {
            throw new DaoException("deletePaymentByNumber() " + e.getMessage());
        }
        return deleted;
    }

    @Override
    public Payment insertPayment(Payment payment) throws DaoException {
        Payment p = null;
        try {
            String query = "INSERT INTO payment (payment_number, booking_number, amount_paid, payment_date, method) VALUES (?,?,?,?,?)";
            int result = helperConnection.executeUpdate(query, payment.getPayment_number(), payment.getBooking_number(), payment.getAmount_paid(), payment.getPayment_date(), payment.getMethod());
            if (result == 1) {
                //add the payment number to the cache
                paymentNumbersCache.add(payment.getPayment_number().toLowerCase());
                Payment insertedPayment = findPaymentByNumber(payment.getPayment_number());
                p = insertedPayment;
            }
        } catch (SQLException e) {
            throw new DaoException("insertPayment() " + e.getMessage());
        }
        return p;
    }

    @Override
    public List<Payment> findAllPaymentsByBookingNumber(String bookingNumber) throws DaoException {
        List<Payment> payments = new ArrayList<>();
        try {
            String query = "SELECT * FROM payment WHERE LOWER(booking_number) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, bookingNumber.toLowerCase());
            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                String paymentNumber = resultSet.getString("payment_number");
                double amountPaid = resultSet.getDouble("amount_paid");
                String paymentDate = resultSet.getString("payment_date");
                String method = resultSet.getString("method");

                Payment p = new Payment(paymentId, paymentNumber, bookingNumber, amountPaid, paymentDate, method);
                payments.add(p);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllPaymentsByBookingNumber() " + e.getMessage());
        }
        return payments;
    }

    @Override
    public Set<String> uniquePaymentMethod() throws DaoException {
        Set<String> paymentMethods = new HashSet<>();
        try {
            String query = "SELECT DISTINCT method FROM payment";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while (resultSet.next()) {
                String method = resultSet.getString("method");
                paymentMethods.add(method);
            }
        } catch (SQLException e) {
            throw new DaoException("uniquePaymentMethod() " + e.getMessage());
        }
        return paymentMethods;
    }

    @Override
    public List<Payment> findPaymentByPaymentMethod(String paymentMethod) throws DaoException {
        List<Payment> payments = new ArrayList<>();
        try {
            String query = "SELECT * FROM payment WHERE LOWER(method) = ?";
            ResultSet resultSet = helperConnection.executeQuery(query, paymentMethod.toLowerCase());
            while (resultSet.next()) {
                int paymentId = resultSet.getInt("payment_id");
                String paymentNumber = resultSet.getString("payment_number");
                String bookingNumber = resultSet.getString("booking_number");
                double amountPaid = resultSet.getDouble("amount_paid");
                String paymentDate = resultSet.getString("payment_date");

                Payment p = new Payment(paymentId, paymentNumber, bookingNumber, amountPaid, paymentDate, paymentMethod);
                payments.add(p);
            }
        } catch (SQLException e) {
            throw new DaoException("findPaymentByPaymentMethod() " + e.getMessage());
        }
        return payments;
    }
}
