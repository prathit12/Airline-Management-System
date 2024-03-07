package Application.BusinessObjects;

import Application.DTOs.*;
import Application.Exceptions.DaoException;
import Application.DAOs.*;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Helpers {
    static CustomerDaoInterface customerDao = new MySqlCustomerDao();
    static AirportDaoInterface airportDao = new MySqlAirportDao();
    static FlightDaoInterface flightDao = new MySqlFlightDao();
    static BookingDaoInterface bookingDao = new MySqlBookingDao();
    static PaymentDaoInterface paymentDao = new MySqlPaymentDao();

    private Scanner input;
    private PrintWriter output;

    public Helpers(Scanner input, PrintWriter output) {
        this.input = input;
        this.output = output;
    }

    public int readInt(String message) {
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            try {
                System.out.print(message);
                input = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter an integer.");
            }
        }
        return input;
    }

    public String readString(String message) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print(message);
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Invalid input, please enter a non-empty string.");
            } else {
                break;
            }
        }
        return input;
    }

    //to read Double
    public double       readDouble(String message) {
        Scanner scanner = new Scanner(System.in);
        double input;
        while (true) {
            try {
                System.out.print(message);
                input = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a double.");
            }
        }
        return input;
    }

    //a method to read a type of String from tge user and do proper check if it snot longer than teh max length
    public String readInputField(String type, int max) {
        String field = "";
        //check if type is customerNumber
        if (type.equalsIgnoreCase("customerNumber")) {
            String customerNumber = "";
            while (customerNumber.isEmpty() || customerNumber.length() > max) {
                customerNumber = readString("Enter customer number (max " + max + " characters): ");
                if (customerNumber.isEmpty()) {
                    System.out.println("Customer number cannot be empty.");
                } else if (customerNumber.length() > max) {
                    System.out.println("Customer number cannot be longer than " + max + " characters.");
                }
                //check if customer number already exists
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
                    if (customer != null) {
                        System.out.println("Customer number already exists. Please enter a different number.");
                        customerNumber = "";
                    }
                }
            }
            field = customerNumber;
        }
        //check if type is customerName
        if (type.equalsIgnoreCase("customerName")) {
            String customerName = "";
            while (customerName.isEmpty() || customerName.length() > max) {
                customerName = readString("Enter customer name (max " + max + " characters): ");
                if (customerName.isEmpty()) {
                    System.out.println("Customer name cannot be empty.");
                } else if (customerName.length() > max) {
                    System.out.println("Customer name cannot be longer than " + max + " characters.");
                }
            }
            field = customerName;
        }
        //check if type is address
        if (type.equalsIgnoreCase("address")) {
            String address = "";
            while (address.isEmpty() || address.length() > max) {
                address = readString("Enter address (max " + max + " characters): ");
                if (address.isEmpty()) {
                    System.out.println("Address cannot be empty.");
                } else if (address.length() > max) {
                    System.out.println("Address cannot be longer than " + max + " characters.");
                }
            }
            field = address;
        }
        //check for airport number
        if (type.equalsIgnoreCase("airportNumber")) {
            String airportNumber = "";
            while (airportNumber.isEmpty() || airportNumber.length() > max) {
                airportNumber = readString("Enter airport number (max " + max + " characters): ");
                if (airportNumber.isEmpty()) {
                    System.out.println("Airport number cannot be empty.");
                } else if (airportNumber.length() > max) {
                    System.out.println("Airport number cannot be longer than " + max + " characters.");
                }
                //check if airport number already exists
                Packet request = new Packet(MenuOptions.AirportMenuOptions.FIND_AIRPORT_BY_NUMBER, airportNumber);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonAirport = (String) response.getData();
                    Airport airport = new Gson().fromJson(jsonAirport, Airport.class);
                    if (airport != null) {
                        System.out.println("Airport number already exists. Please enter a different number.");
                        airportNumber = "";
                    }
                }
            }
            field = airportNumber;
        }
        //check for airport name
        if (type.equalsIgnoreCase("airportName")) {
            String airportName = "";
            while (airportName.isEmpty() || airportName.length() > max) {
                airportName = readString("Enter airport name (max " + max + " characters): ");
                if (airportName.isEmpty()) {
                    System.out.println("Airport name cannot be empty.");
                } else if (airportName.length() > max) {
                    System.out.println("Airport name cannot be longer than " + max + " characters.");
                }
            }
            field = airportName;
        }
        //check for airport location
        if (type.equalsIgnoreCase("airportLocation")) {
            String airportLocation = "";
            while (airportLocation.isEmpty() || airportLocation.length() > max) {
                airportLocation = readString("Enter airport location (max " + max + " characters): ");
                if (airportLocation.isEmpty()) {
                    System.out.println("Airport location cannot be empty.");
                } else if (airportLocation.length() > max) {
                    System.out.println("Airport location cannot be longer than " + max + " characters.");
                }
            }
            field = airportLocation;
        }
        //check for flight number
        if (type.equalsIgnoreCase("flightNumber")) {
            String flightNumber = "";
            while (flightNumber.isEmpty() || flightNumber.length() > max) {
                flightNumber = readString("Enter flight number (max " + max + " characters): ");
                if (flightNumber.isEmpty()) {
                    System.out.println("Flight number cannot be empty.");
                } else if (flightNumber.length() > max) {
                    System.out.println("Flight number cannot be longer than " + max + " characters.");
                }
                //check if flight number already exists
                Packet request = new Packet(MenuOptions.FlightMenuOptions.FIND_FLIGHT_BY_NUMBER, flightNumber);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonFlight = (String) response.getData();
                    Flight flight = new Gson().fromJson(jsonFlight, Flight.class);
                    if (flight != null) {
                        System.out.println("Flight number already exists. Please enter a different number.");
                        flightNumber = "";
                    }
                }
            }
            field = flightNumber;
        }
        //check for flight departure_location
        if (type.equalsIgnoreCase("departureLocation")) {
            String departureLocation = "";
            while (departureLocation.isEmpty() || departureLocation.length() > max) {
                departureLocation = readString("Enter departure location (max " + max + " characters): ");
                if (departureLocation.isEmpty()) {
                    System.out.println("Departure location cannot be empty.");
                } else if (departureLocation.length() > max) {
                    System.out.println("Departure location cannot be longer than " + max + " characters.");
                }
            }
            field = departureLocation;
        }
        //check for flight arrival_location
        if (type.equalsIgnoreCase("arrivalLocation")) {
            String arrivalLocation = "";
            while (arrivalLocation.isEmpty() || arrivalLocation.length() > max) {
                arrivalLocation = readString("Enter arrival location (max " + max + " characters): ");
                if (arrivalLocation.isEmpty()) {
                    System.out.println("Arrival location cannot be empty.");
                } else if (arrivalLocation.length() > max) {
                    System.out.println("Arrival location cannot be longer than " + max + " characters.");
                }
            }
            field = arrivalLocation;
        }
        //check for flight airline_name
        if (type.equalsIgnoreCase("airlineName")) {
            String airlineName = "";
            while (airlineName.isEmpty() || airlineName.length() > max) {
                airlineName = readString("Enter airline name (max " + max + " characters): ");
                if (airlineName.isEmpty()) {
                    System.out.println("Airline name cannot be empty.");
                } else if (airlineName.length() > max) {
                    System.out.println("Airline name cannot be longer than " + max + " characters.");
                }
            }
            field = airlineName;
        }
        //check for booking number
        if (type.equalsIgnoreCase("bookingNumber")) {
            String bookingNumber = "";
            while (bookingNumber.isEmpty() || bookingNumber.length() > max) {
                bookingNumber = readString("Enter booking number (max " + max + " characters): ");
                if (bookingNumber.isEmpty()) {
                    System.out.println("Booking number cannot be empty.");
                } else if (bookingNumber.length() > max) {
                    System.out.println("Booking number cannot be longer than " + max + " characters.");
                }
                //check if booking number already exists
                Packet request = new Packet(MenuOptions.BookingMenuOptions.FIND_BOOKING_BY_NUMBER, bookingNumber);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonBooking = (String) response.getData();
                    Booking booking = new Gson().fromJson(jsonBooking, Booking.class);
                    if (booking != null) {
                        System.out.println("Booking number already exists. Please enter a different number.");
                        bookingNumber = "";
                    }
                }
            }
            field = bookingNumber;
        }
        //check for payment number
        if (type.equalsIgnoreCase("paymentNumber")) {
            String paymentNumber = "";
            while (paymentNumber.isEmpty() || paymentNumber.length() > max) {
                paymentNumber = readString("Enter payment number (max " + max + " characters): ");
                if (paymentNumber.isEmpty()) {
                    System.out.println("Payment number cannot be empty.");
                } else if (paymentNumber.length() > max) {
                    System.out.println("Payment number cannot be longer than " + max + " characters.");
                }
                //check if payment number already exists
                Packet request = new Packet(MenuOptions.PaymentMenuOptions.FIND_PAYMENT_BY_NUMBER, paymentNumber);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonPayment = (String) response.getData();
                    Payment payment = new Gson().fromJson(jsonPayment, Payment.class);
                    if (payment != null) {
                        System.out.println("Payment number already exists. Please enter a different number.");
                        paymentNumber = "";
                    }
                }
            }
            field = paymentNumber;
        }
        return field;
    }

    //a separate method so when inserting in teh floght table, the airport number can be checked if it exists and if it does
    //then insert the airport number in the flight table and if not tell teh user that the airport number does not exist or they
    //can add the airport first
    public String checkAirportNumber() {
        String airportNumber = "";
        while (airportNumber.isEmpty() || airportNumber.length() > 10) {
            airportNumber = readString("Enter airport number (max 10 characters): ");
            if (airportNumber.isEmpty()) {
                System.out.println("Airport number cannot be empty.");
            } else if (airportNumber.length() > 10) {
                System.out.println("Airport number cannot be longer than 10 characters.");
            } else {
//                try {
//                    Airport airport = airportDao.findAirportByNumber(airportNumber);
//                    if (airport == null) {
//                        System.out.println("Airport number does not exist. Please add the airport first in the InsertAirport().");
//                        airportNumber = "";
//                    }
//                } catch (DaoException e) {
//                    System.out.println("Error: " + e.getMessage());
//                }
                Packet request = new Packet(MenuOptions.AirportMenuOptions.FIND_AIRPORT_BY_NUMBER, airportNumber);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonAirport = (String) response.getData();
                    Airport airport = new Gson().fromJson(jsonAirport, Airport.class);
                    if (airport == null) {
                        System.out.println("Airport number does not exist. Please add the airport first in the InsertAirport().");
                        airportNumber = "";
                    }
                }
            }
        }
        return airportNumber;
    }

    //a separate method so when inserting in the booking table, the flight number can be checked if it exists and if it does
    //then insert the flight number in the flight table and if not tell teh user that the flight number does not exist or they
    //can add the flight first
    public String checkFlightNumber() {
        String flightNumber = "";
        while (flightNumber.isEmpty() || flightNumber.length() > 10) {
            flightNumber = readString("Enter flight number (max 10 characters): ");
            if (flightNumber.isEmpty()) {
                System.out.println("Flight number cannot be empty.");
            } else if (flightNumber.length() > 10) {
                System.out.println("Flight number cannot be longer than 10 characters.");
            } else {
//                try {
//                    Flight flight = flightDao.findFlightByNumber(flightNumber);
//                    if (flight == null) {
//                        System.out.println("Flight number does not exist. Please add the flight first in the InsertFlight().");
//                        flightNumber = "";
//                    }
//                } catch (DaoException e) {
//                    System.out.println("Error: " + e.getMessage());
//                }
                Packet request = new Packet(MenuOptions.FlightMenuOptions.FIND_FLIGHT_BY_NUMBER, flightNumber);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonFlight = (String) response.getData();
                    Flight flight = new Gson().fromJson(jsonFlight, Flight.class);
                    if (flight == null) {
                        System.out.println("Flight number does not exist. Please add the flight first in the InsertFlight().");
                        flightNumber = "";
                    }
                }
            }
        }
        return flightNumber;
    }

    //a separate method so when inserting in the booking table, the customer number can be checked if it exists and if it does
    //then insert the customer number in the customer table and if not tell teh user that the customer number does not exist or they
    //can add the customer first
    public String checkCustomerNumber() {
        String customerNumber = "";
        while (customerNumber.isEmpty() || customerNumber.length() > 10) {
            customerNumber = readString("Enter customer number (max 10 characters): ");
            if (customerNumber.isEmpty()) {
                System.out.println("Customer number cannot be empty.");
            } else if (customerNumber.length() > 10) {
                System.out.println("Customer number cannot be longer than 10 characters.");
            } else {
//                try {
//                    Customer customer = customerDao.findCustomerByNumber(customerNumber);
//                    if (customer == null) {
//                        System.out.println("Customer number does not exist. Please add the customer first in the InsertCustomer().");
//                        customerNumber = "";
//                    }
//                } catch (DaoException e) {
//                    System.out.println("Error: " + e.getMessage());
//                }
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
                        System.out.println("Customer number does not exist. Please add the customer first in the InsertCustomer().");
                        customerNumber = "";
                    }
                }
            }
        }
        return customerNumber;
    }

    //a separate method so when inserting in the payment table, the booking number can be checked if it exists and if it does
    //then insert the booking number in the booking table and if not tell teh user that the booking number does not exist or they
    //can add the booking first
    public String checkBookingNumber() {
        String bookingNumber = "";
        while (bookingNumber.isEmpty() || bookingNumber.length() > 10) {
            bookingNumber = readString("Enter booking number (max 10 characters): ");
            if (bookingNumber.isEmpty()) {
                System.out.println("Booking number cannot be empty.");
            } else if (bookingNumber.length() > 10) {
                System.out.println("Booking number cannot be longer than 10 characters.");
            } else {
//                try {
//                    Booking booking = bookingDao.findBookingByNumber(bookingNumber);
//                    if (booking == null) {
//                        System.out.println("Booking number does not exist. Please add the booking first in the InsertBooking().");
//                        bookingNumber = "";
//                    }
//                } catch (DaoException e) {
//                    System.out.println("Error: " + e.getMessage());
//                }
                Packet request = new Packet(MenuOptions.BookingMenuOptions.FIND_BOOKING_BY_NUMBER, bookingNumber);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonBooking = (String) response.getData();
                    Booking booking = new Gson().fromJson(jsonBooking, Booking.class);
                    if (booking == null) {
                        System.out.println("Booking number does not exist. Please add the booking first in the InsertBooking().");
                        bookingNumber = "";
                    }
                }
            }
        }
        return bookingNumber;
    }

    //the method is to check if the email is valid and also if it is not already in the database
    public String readEmail() {
        String email = "";
        while (email.isEmpty() || email.length() > 50) {
            email = readString("Enter email (max 50 characters): ");
            if (email.length() > 50) {
                System.out.println("Error: email cannot be more than 50 characters.");
            } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.out.println("Error: invalid email format.");
                email = "";
            } else {
                try {
                    CustomerDaoInterface customerDao = new MySqlCustomerDao();
                    if (customerDao.checkIfEmailExists(email)) {
                        System.out.println("Error: email already exists and Must be Unique");
                        email = "";
                    }
                } catch (DaoException e) {
                    System.out.println("Error checking email: " + e.getMessage());
                }
            }
        }
        return email;
    }

    //the method is to check if the telephone number is valid
    public String readTelephone() {
        String telNum = "";
        while (telNum.isEmpty() || !telNum.matches("^\\+353\\d{9}$")) {
            telNum = readString("Enter telephone number in Irish format (+353123456789): ");
            if (!telNum.matches("^\\+353\\d{9}$")) {
                System.out.println("Error: invalid telephone number format.");
            }
        }
        return telNum;
    }

    //to validate the flight_cost
    public double readFlightCost() {
        double flightCost = 0;
        while (flightCost <= 0) {
            flightCost = readDouble("Enter flight cost: ");
            if (flightCost <= 0) {
                System.out.println("Flight cost must be a positive number.");
            }
        }
        return flightCost;
    }

    //ro validate the travel_date in format yyyy-mm-dd and also check so that the date is not before today
    //and not more than 12 montsh format yyyy-mm-dd and not more than day 31
    public String readTravelDate() {
        String travelDate = "";
        while (travelDate.isEmpty() || !travelDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            travelDate = readString("Enter travel date in format yyyy-mm-dd: ");
            if (!travelDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                System.out.println("Error: invalid date format.");
            } else {
                String[] date = travelDate.split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                if (year < 2023 || month < 1 || month > 12 || day < 1 || day > 31) {
                    System.out.println("Error: invalid date, You need to put A date in the future.");
                    travelDate = "";
                } else {
                    LocalDate travelDate1 = LocalDate.of(year, month, day);
                    LocalDate today = LocalDate.now();
                    if (travelDate1.isBefore(today)) {
                        System.out.println("Error: travel date cannot be before today.");
                        travelDate = "";
                    }
                }
            }
        }
        return travelDate;
    }

    //to validate the travel_time in format hh:mm:ss and also check so that the
    //time is not before 00:00:00 and not after 23:59:59
    public String readTime() {
        String travelTime = "";
        while (travelTime.isEmpty() || !travelTime.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            travelTime = readString("Enter travel time in format hh:mm:ss: ");
            if (!travelTime.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
                System.out.println("Error: invalid time format.");
            } else {
                String[] time = travelTime.split(":");
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);
                int second = Integer.parseInt(time[2]);
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
                    System.out.println("Error: invalid time format.");
                    travelTime = "";
                }
            }
        }
        return travelTime;
    }

    //to validate the seat_number not less taht 1A and more than 100F
    public String readSeatNumber() {
        String seatNumber = "";
        while (seatNumber.isEmpty() || !seatNumber.matches("^[1-9][0-9]?[A-F]$")) {
            seatNumber = readString("Enter seat number (1A-100F): ");
            if (!seatNumber.matches("^[1-9][0-9]?[A-F]$")) {
                System.out.println("Error: invalid seat number format.");
            }
        }
        return seatNumber;
    }

    //to validate the amountpaid for the payment table
    public double readAmountPaid() {
        double amountPaid = 0;
        while (amountPaid <= 0) {
            amountPaid = readDouble("Enter amount paid: ");
            if (amountPaid <= 0) {
                System.out.println("Amount paid must be a positive number.");
            }
        }
        return amountPaid;
    }

    //to validate the payment_date in format yyyy-mm-dd and also check so that the date is not before today
    //and not more than 12 montsh format yyyy-mm-dd and not more than day 31
    public String readPaymentDate() {
        String paymentDate = "";
        while (paymentDate.isEmpty() || !paymentDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            paymentDate = readString("Enter payment date in format yyyy-mm-dd: ");
            if (!paymentDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                System.out.println("Error: invalid date format.");
            } else {
                String[] date = paymentDate.split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                if (year < 2023 || month < 1 || month > 12 || day < 1 || day > 31) {
                    System.out.println("Error: invalid date.");
                    paymentDate = "";
                } else {
                    LocalDate paymentDate1 = LocalDate.of(year, month, day);
                    LocalDate today = LocalDate.now();
                    if (paymentDate1.isAfter(today)) {
                        System.out.println("Error: payment date cannot be After today(No Payment On Credit).");
                        paymentDate = "";
                    }
                }
            }
        }
        return paymentDate;
    }
}
