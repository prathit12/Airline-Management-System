package Application.BusinessObjects;

import Application.DAOs.*;
import Application.DTOs.*;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import java.lang.reflect.Type;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CustomerObj {
    static Helpers helper;
    static CustomerDaoInterface customerDao = new MySqlCustomerDao();

    static BookingDaoInterface bookingDao = new MySqlBookingDao();

    private TreeSet<String> customerNumbersCache;

    private Scanner input;
    private PrintWriter output;

    public CustomerObj(Scanner input, PrintWriter output) {
        this.input = input;
        this.output = output;
        this.customerNumbersCache = new TreeSet<>();
    }

    public void fetchCustomerNumbersCache() {
        //first we need to clear the cache
        customerNumbersCache.clear();
        Packet request = new Packet(MenuOptions.CustomerMenuOptions.GET_CUSTOMER_NUMBERS_CACHE, null);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
        } else {
            String jsonCache = (String) response.getData();
            Type cacheType = new TypeToken<TreeSet<String>>(){}.getType();
            customerNumbersCache = new Gson().fromJson(jsonCache, cacheType);
        }
    }

    //display all customers
    public void findAllCustomers() {
        Packet request = new Packet(MenuOptions.CustomerMenuOptions.FIND_ALL_CUSTOMERS, null);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
        } else {
            String jsonCustomers = (String) response.getData();
            Type customerListType = new TypeToken<List<Customer>>(){}.getType();
            List<Customer> customers = new Gson().fromJson(jsonCustomers, customerListType);

            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            }
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        }
    }

    //to find customer by number and also if no customer found then it will display no customer found
    //and if customer found then it will display customer details(customerNumber is a String)
    public void findCustomerByNumber() {
        helper = new Helpers(input, output);
        String customerNumber = helper.readString("Enter customer number: ");
        if(!customerNumbersCache.contains(customerNumber.toLowerCase()) && !customerNumbersCache.contains(customerNumber.toUpperCase())) {
            System.out.println("Customer number not found.");
            return;
        }
        Packet request = new Packet(MenuOptions.CustomerMenuOptions.FIND_CUSTOMER_BY_NUMBER, customerNumber);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
        } else {
            String jsonCustomer = (String) response.getData();
            Customer customer = new Gson().fromJson(jsonCustomer, Customer.class);
            if (customer == null) {
                System.out.println("No customer found.");
            } else {
                System.out.println(customer);
            }
        }
    }

//to delete customer by number and also if no customer found then it will display no customer found
    public void deleteCustomerByNumber() {
        helper = new Helpers(input, output);
        String customerNumber = helper.readString("Enter customer number: ");
        if(!customerNumbersCache.contains(customerNumber.toLowerCase()) && !customerNumbersCache.contains(customerNumber.toUpperCase())) {
            System.out.println("Customer number not found.");
            return;
        }
        Packet request = new Packet(MenuOptions.CustomerMenuOptions.DELETE_CUSTOMER_BY_NUMBER, customerNumber);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
//        System.out.println("Client: Received JSON: " + jsonResponse); // Debugging line
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            String exceptionMessage = response.getException().getMessage();
//            System.out.println("Error: " + (exceptionMessage == null ? "Unknown error" : exceptionMessage));

            if (response.getData() != null) {
                System.out.println("Customer-" + customerNumber + " cannot be deleted because it has related records in the database:");

                // The related bookings can be sent as part of the response in the 'data' field.
                String jsonBookings = (String) response.getData();
                Type bookingListType = new TypeToken<List<Booking>>(){}.getType();
                List<Booking> bookings = new Gson().fromJson(jsonBookings, bookingListType);

                for (Booking booking : bookings) {
                    System.out.println(booking);
                }
            }
        } else {
            boolean deleted = Boolean.parseBoolean((String) response.getData());
            if (deleted) {
                System.out.println("Customer deleted.");
                customerNumbersCache.remove(customerNumber.toLowerCase());
            } else {
                System.out.println("No customer found.");
            }
        }
    }

    //to insert customer and also if customer number already exist then it will display customer number already exist
    public void insertCustomer() {
        helper = new Helpers(input, output);
        String customerNumber = helper.readInputField("customerNumber", 10);
        String customerName = helper.readInputField("customerName", 50);
        String email = helper.readEmail();
        String telephone = helper.readTelephone();
        String address = helper.readInputField("address", 50);

        Customer customer = new Customer(customerNumber, customerName, email, telephone, address);
        Packet request = new Packet(MenuOptions.CustomerMenuOptions.INSERT_CUSTOMER, customer);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
//        System.out.println("Client: Received JSON: " + jsonResponse); // Debugging line
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() !=null) {
            System.out.println("Client: Error inserting customer: " + response.getExceptionMessage()); // Debugging line
        } else {
            String jsonCustomer = (String) response.getData();
            Customer c = new Gson().fromJson(jsonCustomer, Customer.class);
            if (c == null) {
                System.out.println("Client: Customer Was Not Inserted."); // Debugging line
            } else {
                System.out.println(c);
                System.out.println("Client: Customer inserted.");
                customerNumbersCache.add(customer.getCustomer_number().toLowerCase());
            }
        }
    }

    //this is whreer i implememted my join query that will display customer details with booking details and fkight as well
    public void findRelatedBookingsWithFlightsForCustomers() {
        Packet request = new Packet(MenuOptions.CustomerMenuOptions.FIND_ALL_CUSTOMERS_BOOKINGS_FLIGHTS, null);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
        } else {
            String jsonCustomersBookingsFlights = (String) response.getData();
            Type customerBookingListType = new TypeToken<List<CustomerBooking>>(){}.getType();
            List<CustomerBooking> customerBookingFlights = new Gson().fromJson(jsonCustomersBookingsFlights, customerBookingListType);

            if (customerBookingFlights.isEmpty()) {
                System.out.println("No customers with Bookings and Related Flights found.");
            }
            for (CustomerBooking customerBookingFlight : customerBookingFlights) {
                System.out.println(customerBookingFlight.toString());
            }
        }
    }
}
