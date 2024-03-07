package Application.Client;

import Application.BusinessObjects.*;
import Application.DAOs.*;
import Application.Exceptions.DaoException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    //for my helper functions
    static Helpers helper;

    //to access the customer fuctions
    static CustomerObj customerObj;

    //to access the airport functions
    static AirportObj airportObj;

    //to access the flight functions
    static FlightObj flightObj;

    //to access the booking functions
    static BookingObj bookingObj;

    //to access the payment functions
    static PaymentObj paymentObj;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public static void start() {
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client message: The Client is running and has connected to the server");

//Step 2: Build input and output streams linked to the socket
            OutputStream out = socket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));
            InputStream in = socket.getInputStream();
            Scanner input = new Scanner(new InputStreamReader(in));

            customerObj = new CustomerObj(input, output);
            airportObj = new AirportObj(input, output);
            flightObj = new FlightObj(input, output);
            helper = new Helpers(input, output);
            bookingObj = new BookingObj(input, output);
            paymentObj = new PaymentObj(input, output);

            while (true) {
                //these are to initialise the cache for the client side
                customerObj.fetchCustomerNumbersCache();
                airportObj.fetchAirportNumbersCache();
                flightObj.fetchFlightNumbersCache();
                bookingObj.fetchBookingNumbersCache();
                paymentObj.fetchPaymentNumbersCache();
                printMainMenu();
                int choice = helper.readInt("Enter your choice: ");
                switch (choice) {
                    case 1:
                        findAllEntitiesMenu();
                        break;
                    case 2:
                        findByNumberMenu();
                        break;
                    case 3:
                        deleteMenu();
                        break;
                    case 4:
                        insertMenu();
                        break;
                    case 5:
                        filterMenu();
                        break;
                    case 6:
                        System.out.println("Exiting program...");
                        socket.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice, please try again.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printMainMenu() {
        System.out.println("--- Main Menu ---");
        System.out.println("1. Find All Entities");
        System.out.println("2. Find Entity by Number");
        System.out.println("3. Delete Entity by Number");
        System.out.println("4. Insert Entity");
        System.out.println("5. Filter Entity");
        System.out.println("6. Exit");
    }

    private static void findAllEntitiesMenu() {
        while (true) {
            System.out.println("--- Find All Entities ---");
            System.out.println("1. Find All Customers");
            System.out.println("2. Find All Airports");
            System.out.println("3. Find All Flights");
            System.out.println("4. Find All Bookings");
            System.out.println("5. Find All Payments");
            System.out.println("6. Find Related Bookings With Flights For A Customer");
            System.out.println("7. Back to Main Menu");
            int choice = helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    customerObj.findAllCustomers();
                    break;
                case 2:
                    airportObj.findAllAirports();
                    break;
                case 3:
                    flightObj.findAllFlights();
                    break;
                case 4:
                    bookingObj.findAllBookings();
                    break;
                case 5:
                    paymentObj.findAllPayments();
                    break;
                case 6:
                    customerObj.findRelatedBookingsWithFlightsForCustomers();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static void findByNumberMenu() {
        while (true) {
            System.out.println("--- Find Entity by Number ---");
            System.out.println("1. Find Customer by Number");
            System.out.println("2. Find Airport by Number");
            System.out.println("3. Find Flight by Number");
            System.out.println("4. Find Booking by Number");
            System.out.println("5. Find Payment by Number");
            System.out.println("6. Back to Main Menu");
            int choice = helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    customerObj.findCustomerByNumber();
                    break;
                case 2:
                    airportObj.findAirportByNumber();
                    break;
                case 3:
                    flightObj.findFlightByNumber();
                    break;
                case 4:
                    bookingObj.findBookingByNumber();
                    break;
                case 5:
                    paymentObj.findPaymentByNumber();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static void deleteMenu() {
        while (true) {
            System.out.println("--- Delete Entity by Number ---");
            System.out.println("1. Delete Customer by Number");
            System.out.println("2. Delete Airport by Number");
            System.out.println("3. Delete Flight by Number");
            System.out.println("4. Delete Booking by Number");
            System.out.println("5. Delete Payment by Number");
            System.out.println("6. Back to Main Menu");
            int choice = helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    customerObj.deleteCustomerByNumber();
                    break;
                case 2:
                    airportObj.deleteAirportByNumber();
                    break;
                case 3:
                    flightObj.deleteFlightByNumber();
                    break;
                case 4:
                    bookingObj.deleteBookingByNumber();
                    break;
                case 5:
                    paymentObj.deletePaymentByNumber();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private static void insertMenu() {
        while (true) {
            System.out.println("--- Insert Entity ---");
            System.out.println("1. Insert Customer");
            System.out.println("2. Insert Airport");
            System.out.println("3. Insert Flight");
            System.out.println("4. Insert Booking");
            System.out.println("5. Insert Payment");
            System.out.println("6. Back to Main Menu");
            int choice = helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    customerObj.insertCustomer();
                    break;
                case 2:
                    airportObj.insertAirport();
                    break;
                case 3:
                    flightObj.insertFlight();
                    break;
                case 4:
                    bookingObj.insertBooking();
                    break;
                case 5:
                    paymentObj.insertPayment();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    //menu to filter
    private static void filterMenu() {
        while (true) {
            System.out.println("--- Filter Entity ---");
            System.out.println("1. Filter Airport With City");
            System.out.println("2. Filter Flights With Airline");
            System.out.println("3. Filter Flights By The Time Of The Day");
            System.out.println("4. Filter Payment By Method");
            System.out.println("5. Back to Main Menu");
            int choice = helper.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    airportObj.filterAirportByCity();
                    break;
                case 2:
                    flightObj.filterFlightByAirline();
                    break;
                case 3:
                    flightObj.filterFlightByDepartureTime();
                    break;
                case 4:
                    paymentObj.filterPaymentByPaymentMethod();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }
}
