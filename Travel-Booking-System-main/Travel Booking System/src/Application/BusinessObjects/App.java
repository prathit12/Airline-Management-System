//package Application.BusinessObjects;
//
//import java.util.Scanner;
//
//import Application.DAOs.*;
//import Application.DTOs.*;
//import Application.Exceptions.DaoException;
//import Application.BusinessObjects.Helpers;
//import java.util.List;
//import java.time.LocalDate;
//
//
//public class App {
//    //for my helper functions
//    static Helpers helper = new Helpers();
//
//    //to access the customer fuctions
//    static CustomerObj customerObj = new CustomerObj();
//
//    //to access the airport functions
//    static AirportObj airportObj = new AirportObj();
//
//    //to access the flight functions
//    static FlightObj flightObj = new FlightObj();
//
//    //to access the booking functions
//    static BookingObj bookingObj = new BookingObj();
//
//    //to access the payment functions
//    static PaymentObj paymentObj = new PaymentObj();
//
//    public static void main(String[] args) {
//        autoInitialize();
//        while (true) {
//            printMainMenu();
//            int choice = helper.readInt("Enter your choice: ");
//            switch (choice) {
//                case 1:
//                    findAllEntitiesMenu();
//                    break;
//                case 2:
//                    findByNumberMenu();
//                    break;
//                case 3:
//                    deleteMenu();
//                    break;
//                case 4:
//                    insertMenu();
//                    break;
//                case 5:
//                    filterMenu();
//                    break;
//                case 6:
//                    System.out.println("Exiting program...");
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid choice, please try again.");
//                    break;
//            }
//        }
//    }
//
//    private static void printMainMenu() {
//        System.out.println("--- Main Menu ---");
//        System.out.println("1. Find All Entities");
//        System.out.println("2. Find Entity by Number");
//        System.out.println("3. Delete Entity by Number");
//        System.out.println("4. Insert Entity");
//        System.out.println("5. Filter Entity");
//        System.out.println("6. Exit");
//    }
//
//    private static void findAllEntitiesMenu() {
//        while (true) {
//            System.out.println("--- Find All Entities ---");
//            System.out.println("1. Find All Customers");
//            System.out.println("2. Find All Airports");
//            System.out.println("3. Find All Flights");
//            System.out.println("4. Find All Bookings");
//            System.out.println("5. Find All Payments");
//            System.out.println("6. Back to Main Menu");
//            int choice = helper.readInt("Enter your choice: ");
//            switch (choice) {
//                case 1:
//                    customerObj.findAllCustomers();
//                    break;
//                case 2:
//                    airportObj.findAllAirports();
//                    break;
//                case 3:
//                    flightObj.findAllFlights();
//                    break;
//                case 4:
//                    bookingObj.findAllBookings();
//                    break;
//                case 5:
//                    paymentObj.findAllPayments();
//                    break;
//                case 6:
//                    return;
//                default:
//                    System.out.println("Invalid choice, please try again.");
//                    break;
//            }
//        }
//    }
//
//    private static void findByNumberMenu() {
//        while (true) {
//            System.out.println("--- Find Entity by Number ---");
//            System.out.println("1. Find Customer by Number");
//            System.out.println("2. Find Airport by Number");
//            System.out.println("3. Find Flight by Number");
//            System.out.println("4. Find Booking by Number");
//            System.out.println("5. Find Payment by Number");
//            System.out.println("6. Back to Main Menu");
//            int choice = helper.readInt("Enter your choice: ");
//            switch (choice) {
//                case 1:
//                    customerObj.findCustomerByNumber();
//                    break;
//                case 2:
//                    airportObj.findAirportByNumber();
//                    break;
//                case 3:
//                    flightObj.findFlightByNumber();
//                    break;
//                case 4:
//                    bookingObj.findBookingByNumber();
//                    break;
//                case 5:
//                    paymentObj.findPaymentByNumber();
//                    break;
//                case 6:
//                    return;
//                default:
//                    System.out.println("Invalid choice, please try again.");
//                    break;
//            }
//        }
//    }
//
//    private static void deleteMenu() {
//        while (true) {
//            System.out.println("--- Delete Entity by Number ---");
//            System.out.println("1. Delete Customer by Number");
//            System.out.println("2. Delete Airport by Number");
//            System.out.println("3. Delete Flight by Number");
//            System.out.println("4. Delete Booking by Number");
//            System.out.println("5. Delete Payment by Number");
//            System.out.println("6. Back to Main Menu");
//            int choice = helper.readInt("Enter your choice: ");
//            switch (choice) {
//                case 1:
//                    customerObj.deleteCustomerByNumber();
//                    break;
//                case 2:
//                    airportObj.deleteAirportByNumber();
//                    break;
//                case 3:
//                    flightObj.deleteFlightByNumber();
//                    break;
//                case 4:
//                    bookingObj.deleteBookingByNumber();
//                    break;
//                case 5:
//                    paymentObj.deletePaymentByNumber();
//                    break;
//                case 6:
//                    return;
//                default:
//                    System.out.println("Invalid choice, please try again.");
//                    break;
//            }
//        }
//    }
//
//    private static void insertMenu() {
//        while (true) {
//            System.out.println("--- Insert Entity ---");
//            System.out.println("1. Insert Customer");
//            System.out.println("2. Insert Airport");
//            System.out.println("3. Insert Flight");
//            System.out.println("4. Insert Booking");
//            System.out.println("5. Insert Payment");
//            System.out.println("6. Back to Main Menu");
//            int choice = helper.readInt("Enter your choice: ");
//            switch (choice) {
//                case 1:
//                    customerObj.insertCustomer();
//                    break;
//                case 2:
//                    airportObj.insertAirport();
//                    break;
//                case 3:
//                    flightObj.insertFlight();
//                    break;
//                case 4:
//                    bookingObj.insertBooking();
//                    break;
//                case 5:
//                    paymentObj.insertPayment();
//                    break;
//                case 6:
//                    return;
//                default:
//                    System.out.println("Invalid choice, please try again.");
//                    break;
//            }
//        }
//    }
//
//    //menu to filter
//    private static void filterMenu() {
//        while (true) {
//            System.out.println("--- Filter Entity ---");
//            System.out.println("1. Filter Airport With City");
//            System.out.println("2. Filter Flights With Airline");
//            System.out.println("3. Filter Flights By The Time Of The Day");
//            System.out.println("4. Filter Payment By Method");
//            System.out.println("5. Back to Main Menu");
//            int choice = helper.readInt("Enter your choice: ");
//            switch (choice) {
//                case 1:
//                    airportObj.filterAirportByCity();
//                    break;
//                case 2:
//                    flightObj.filterFlightByAirline();
//                    break;
//                case 3:
//                    flightObj.filterFlightByDepartureTime();
//                    break;
//                case 4:
//                    paymentObj.filterPaymentByPaymentMethod();
//                    break;
//                case 5:
//                    return;
//                default:
//                    System.out.println("Invalid choice, please try again.");
//                    break;
//            }
//        }
//    }
//
//    //TO autoinitialize the cache
//    private static void autoInitialize() {
//        MySqlCustomerDao mySqlCustomerDao = new MySqlCustomerDao();
//        MySqlAirportDao mySqlAirportDao = new MySqlAirportDao();
//        MySqlFlightDao mySqlFlightDao = new MySqlFlightDao();
//        MySqlBookingDao mySqlBookingDao = new MySqlBookingDao();
//        MySqlPaymentDao mySqlPaymentDao = new MySqlPaymentDao();
//        try {
//            mySqlCustomerDao.populateCustomerCache();
//            mySqlAirportDao.populateAirportCache();
//            mySqlFlightDao.populateFlightCache();
//            mySqlBookingDao.populateBookingCache();
//            mySqlPaymentDao.populatePaymentCache();
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//    }
//}
