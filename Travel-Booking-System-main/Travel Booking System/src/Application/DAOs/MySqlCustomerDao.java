package Application.DAOs;

import Application.DAOs.MySqlDao;
import Application.Exceptions.DaoException;
import Application.DAOs.CustomerDaoInterface;
import Application.DTOs.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class MySqlCustomerDao extends  MySqlDao implements CustomerDaoInterface{
    private static TreeSet<String> customerNumbersCache = new TreeSet<>();

    //to get the helper connection
    private HelperConnection helperConnection = new HelperConnection();

    @Override
    public TreeSet<String> populateCustomerCache() throws DaoException {
        customerNumbersCache.clear();
        try{
            String query = "SELECT customer_number FROM customer";
            ResultSet resultSet = helperConnection.executeQuery(query);

            while(resultSet.next()){
                String customerNumber = resultSet.getString("customer_number");
                //add the customer number to the cache
                customerNumbersCache.add(customerNumber.toLowerCase());
            }
        }catch(SQLException e){
            throw new DaoException("populateCustomerCache() " + e.getMessage());
        }
        return customerNumbersCache;
    }

    @Override
    public List<Customer> findAllCustomers() throws DaoException {
        List<Customer> customers = new ArrayList<>();
        try{
            String query = "SELECT * FROM customer";
            ResultSet resultSet = helperConnection.executeQuery(query);
            while(resultSet.next()){
                int customerId = resultSet.getInt("customer_id");
                String customerNumber = resultSet.getString("customer_number");
                String customerName = resultSet.getString("customer_name");
                String customerEmail = resultSet.getString("email");
                String customerPhone = resultSet.getString("tel_num");
                String customerAddress = resultSet.getString("address");

                Customer c = new Customer(customerId,customerNumber,customerName,customerEmail,customerPhone,customerAddress);
                customers.add(c);
            }

        }catch(SQLException e){
            throw new DaoException("findAllCustomersresultSet() " + e.getMessage());
        }
        return customers;
    }

    @Override
    public Customer findCustomerByNumber(String customerNumber) throws DaoException {
        Customer c = null;
        //check if the customer number is in the cache with both lower and upper case
        if(!customerNumbersCache.contains(customerNumber.toLowerCase()) && !customerNumbersCache.contains(customerNumber.toUpperCase())){
            return null;
        }
        try{
            String query = "SELECT * FROM customer WHERE LOWER(customer_number) = LOWER(?)";
            ResultSet resultSet = helperConnection.executeQuery(query, customerNumber);
            if(resultSet.next()){
                int customerId = resultSet.getInt("customer_id");
                String customerName = resultSet.getString("customer_name");
                String customerEmail = resultSet.getString("email");
                String customerPhone = resultSet.getString("tel_num");
                String customerAddress = resultSet.getString("address");

                c = new Customer(customerId,customerNumber, customerName, customerEmail, customerPhone, customerAddress);
            }
        } catch(SQLException e){
            throw new DaoException("findCustomerByNumber() " + e.getMessage());
        }
        return c;
    }

    @Override
    public boolean deleteCustomerByNumber(String customerNumber) throws DaoException {
        boolean deleted = false;
        //check if the customer number is in the cache with both lower and upper case
        if(!customerNumbersCache.contains(customerNumber.toLowerCase()) && !customerNumbersCache.contains(customerNumber.toUpperCase())){
            return false;
        }
        try {
            String query = "DELETE FROM customer WHERE LOWER(customer_number) = LOWER(?)";
            int rowsAffected = helperConnection.executeUpdate(query, customerNumber);
             if(rowsAffected == 1){
                deleted = true;

                //remove the customer number from the cache
                customerNumbersCache.remove(customerNumber.toLowerCase());
            }
        } catch (SQLException e) {
            throw new DaoException("deleteCustomerByNumber() " + e.getMessage());
        }
        return deleted;
    }

    @Override
    public Customer insertCustomer(Customer customer) throws DaoException {
        Customer c = null;
        try {
            String query = "INSERT INTO customer (customer_number, customer_name, email, tel_num, address) VALUES (?,?,?,?,?)";
            int result = helperConnection.executeUpdate(query, customer.getCustomer_number(), customer.getCustomer_name(), customer.getEmail(), customer.getTel_num(), customer.getAddress());
            if (result == 1) {
                //add the customer number to the cache
                customerNumbersCache.add(customer.getCustomer_number().toLowerCase());
                Customer insertedCustomer = findCustomerByNumber(customer.getCustomer_number());
                c = insertedCustomer;

            }
        } catch (SQLException e) {
            throw new DaoException("insertCustomerresultSet() " + e.getMessage());
        }
        return c;
    }

    @Override
    public boolean checkIfEmailExists(String email) throws DaoException {
        boolean exists = false;

        try {
            String query = "SELECT * FROM customer WHERE LOWER(email) = LOWER(?)";
            ResultSet resultSet = helperConnection.executeQuery(query, email);
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            throw new DaoException("checkIfEmailExists() " + e.getMessage());
        }
        return exists;
    }
}
