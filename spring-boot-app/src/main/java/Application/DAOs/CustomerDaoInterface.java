package Application.DAOs;

import Application.DTOs.Customer;
import Application.Exceptions.DaoException;

import java.util.List;
import java.util.TreeSet;

public interface CustomerDaoInterface {
    public List<Customer> findAllCustomers() throws DaoException;
    public Customer findCustomerByNumber(String customerNumber) throws DaoException;
    public boolean deleteCustomerByNumber(String customerNumber) throws DaoException;
    public Customer insertCustomer(Customer customer) throws DaoException;
    public boolean checkIfEmailExists(String email) throws DaoException;
    public TreeSet<String> populateCustomerCache() throws DaoException;
}