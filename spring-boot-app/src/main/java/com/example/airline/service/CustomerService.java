package com.example.airline.service;

import Application.DAOs.CustomerDaoInterface;
import Application.DAOs.MySqlCustomerDao;
import Application.DTOs.Customer;
import Application.Exceptions.DaoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

@Service
public class CustomerService {
    private final CustomerDaoInterface customerDao = new MySqlCustomerDao();

    public List<Customer> findAllCustomers() throws DaoException {
        return customerDao.findAllCustomers();
    }

    public Customer findCustomerByNumber(String customerNumber) throws DaoException {
        return customerDao.findCustomerByNumber(customerNumber);
    }

    public Customer insertCustomer(Customer customer) throws DaoException {
        return customerDao.insertCustomer(customer);
    }

    public boolean deleteCustomerByNumber(String customerNumber) throws DaoException {
        return customerDao.deleteCustomerByNumber(customerNumber);
    }

    public boolean checkIfEmailExists(String email) throws DaoException {
        return customerDao.checkIfEmailExists(email);
    }

    public TreeSet<String> populateCustomerCache() throws DaoException {
        return customerDao.populateCustomerCache();
    }
}