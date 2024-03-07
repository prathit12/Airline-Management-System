package Application.JunitTests;

import static org.junit.jupiter.api.Assertions.*;
import Application.DAOs.CustomerDaoInterface;
import Application.DAOs.MySqlCustomerDao;
import Application.DTOs.Customer;

import java.util.List;

class MySqlCustomerDaoTest {

    @org.junit.jupiter.api.Test
    void testFindAllCustomers(){
        CustomerDaoInterface customerDao = new MySqlCustomerDao();
        try {
            List<Customer> customers = customerDao.findAllCustomers();
            //this is a test to see if the list is empty
            assertNotNull(customers);
            //if the list is not empty, then the size of the list should be greater than 0 which is true
            assertTrue(customers.size() > 0);
            for (Customer c : customers) {
                System.out.println(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this is a test to see if the customer is found by number
    @org.junit.jupiter.api.Test
    void testFindCustomerByNumber(){
        CustomerDaoInterface customerDao = new MySqlCustomerDao();
        String customerNumber = "C0001";
        try{
            Customer customer = customerDao.findCustomerByNumber(customerNumber);
            //this is a test to see if the customer is found by number
            //if the customer is found by number, then the customer should not be null
            assertNotNull(customer);
            System.out.println(customer);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this is a test to see if the customer is deleted by id
    //this is a test to see if the customer is deleted by number
    @org.junit.jupiter.api.Test
    void testDeleteCustomerByNumber(){
        CustomerDaoInterface customerDao = new MySqlCustomerDao();
        String customerNumber = "C0013";
        try{
            boolean deleted = customerDao.deleteCustomerByNumber(customerNumber);
            //this is a test to see if the customer is deleted by number
            //if the customer is deleted by number, then the customer should be true
            assertTrue(deleted);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this is a test to see if the customer is inserted
    @org.junit.jupiter.api.Test
    void testInsertCustomer(){
        CustomerDaoInterface customerDao = new MySqlCustomerDao();
        Customer customer = new Customer("C0012","Pack","5@student.dkit.ie","0871234567","Dundalk");
        try{
            Customer insertedCustomer = customerDao.insertCustomer(customer);
            //this is a test to see if the customer is inserted
            //if the customer is inserted, then the customer should not be null
            assertNotNull(insertedCustomer);
            System.out.println(insertedCustomer);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test to check if a customer is trying to input an email that already exists
    @org.junit.jupiter.api.Test
    void testCheckIfEmailExists(){
        CustomerDaoInterface customerDao = new MySqlCustomerDao();
        String email = "john.smith@example.com";
        try{
            boolean emailExists = customerDao.checkIfEmailExists(email);
            //this is a test to see if the email exists
            //if the email exists, then the email should be true
            assertTrue(emailExists);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}