package com.example.airline.controller;

import Application.DTOs.Customer;
import Application.Exceptions.DaoException;
import com.example.airline.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() throws DaoException {
        return customerService.findAllCustomers();
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerNumber) throws DaoException {
        Customer customer = customerService.findCustomerByNumber(customerNumber);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) throws DaoException {
        return customerService.insertCustomer(customer);
    }

    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerNumber) throws DaoException {
        boolean deleted = customerService.deleteCustomerByNumber(customerNumber);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) throws DaoException {
        boolean exists = customerService.checkIfEmailExists(email);
        return ResponseEntity.ok(exists);
    }
}