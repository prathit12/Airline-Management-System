package Application.JunitTests;

import Application.DAOs.MySqlPaymentDao;
import Application.DAOs.PaymentDaoInterface;
import Application.DTOs.Payment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlPaymentDaoTest {
    @Test
    void testFindAllPayments() {
        PaymentDaoInterface paymentDao = new MySqlPaymentDao();

        try{
            List<Payment> payments = paymentDao.findAllPayments();
            //this is a test to see if the list is empty
            assertNotNull(payments);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(payments.size() > 0);
            for (Payment p : payments) {
                System.out.println(p);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to find payment by number
    @Test
    void testFindPaymentByNumber(){
        PaymentDaoInterface paymentDao = new MySqlPaymentDao();
        String paymentNumber = "P0007";
        try{
            Payment payment = paymentDao.findPaymentByNumber(paymentNumber);
            //this is a test to see if the payment is found by number
            //if the payment is found by number, then the payment should not be null
            assertNotNull(payment);
            System.out.println(payment);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to delete payment by number
    @Test
    void testDeletePaymentByNumber(){
        PaymentDaoInterface paymentDao = new MySqlPaymentDao();
        String paymentNumber = "P0041";

        try{
            boolean deleted = paymentDao.deletePaymentByNumber(paymentNumber);
            //this is a test to see if the payment is deleted by number
            //if the payment is deleted by number, then the payment should be true
            assertTrue(deleted);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to insert payment
    @Test
    void testInsertPayment(){
        PaymentDaoInterface paymentDao = new MySqlPaymentDao();
        Payment payment = new Payment("P0041", "B0007", 175.0, "2023-03-31", "PayPal");
        try{
            Payment insertedPayment = paymentDao.insertPayment(payment);
            //this is a test to see if the payment is inserted
            //if the payment is inserted, then the inserted payment should not be null
            assertNotNull(insertedPayment);
            System.out.println(insertedPayment);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //find all payments by booking number
    @Test
    void testFindAllPaymentsByBookingNumber(){
        PaymentDaoInterface paymentDao = new MySqlPaymentDao();
        String bookingNumber = "B0007";
        try{
            List<Payment> payments = paymentDao.findAllPaymentsByBookingNumber(bookingNumber);
            //this is a test to see if the list is empty
            assertNotNull(payments);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(payments.size() > 0);
            for (Payment p : payments) {
                System.out.println(p);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}