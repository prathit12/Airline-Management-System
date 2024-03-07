package Application.Server;

import Application.DAOs.*;
import Application.Protocol.MenuOptions;
import Application.Server.AirportCommands.*;
import Application.Server.BookingCommands.*;
import Application.Server.CustomerCommands.*;
import Application.Server.FlightCommands.*;
import Application.Server.PaymentCommands.*;

import static Application.Protocol.MenuOptions.BookingMenuOptions.*;
import static Application.Protocol.MenuOptions.CustomerMenuOptions.*;
import static Application.Protocol.MenuOptions.AirportMenuOptions.*;
import static Application.Protocol.MenuOptions.FlightMenuOptions.*;
import static Application.Protocol.MenuOptions.PaymentMenuOptions.*;

public class CommandFactory {
    private CustomerDaoInterface customerDao = new MySqlCustomerDao();
    private AirportDaoInterface airportDao = new MySqlAirportDao();
    private FlightDaoInterface flightDao = new MySqlFlightDao();
    private BookingDaoInterface bookingDao = new MySqlBookingDao();
    private PaymentDaoInterface paymentDao = new MySqlPaymentDao();
    private CustomerBookingDaoInterface customerBookingDao = new MySqlCustomerBookingDao();

    public Command createCustomerCommand(MenuOptions.CustomerMenuOptions option) {
        if (option.equals(FIND_ALL_CUSTOMERS)) {
            return new FindAllCustomersCommand(customerDao);
        } else if (option.equals(FIND_CUSTOMER_BY_NUMBER)) {
            return new FindCustomerByNumberCommand(customerDao);
        } else if (option.equals(DELETE_CUSTOMER_BY_NUMBER)) {
            return new DeleteCustomerByNumberCommand(customerDao, bookingDao);
        } else if (option.equals(INSERT_CUSTOMER)) {
            return new InsertCustomerCommand(customerDao);
        } else if (option.equals(CHECK_DUPLICATE_EMAIL)) {
            return new CheckDuplicateEmailCommand(customerDao);
        } else if (option.equals(GET_CUSTOMER_NUMBERS_CACHE)) {
            return new GetCustomerNumbersCacheCommand(customerDao);
        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }

    public Command createAirportCommand(MenuOptions.AirportMenuOptions option) {
        if (option.equals(FIND_ALL_AIRPORTS)) {
            return new FindAllAirportsCommand(airportDao);
        } else if (option.equals(FIND_AIRPORT_BY_NUMBER)) {
            return new FindAirportByNumberCommand(airportDao);
        } else if (option.equals(DELETE_AIRPORT_BY_NUMBER)) {
            return new DeleteAirportByNumberCommand(airportDao, flightDao);
        } else if (option.equals(INSERT_AIRPORT)) {
            return new InsertAirportCommand(airportDao);
        } else if (option.equals(FILTER_AIRPORT_BY_CITY)) {
            return new FilterAirportByCityCommand(airportDao);
        } else if (option.equals(FIND_AIRPORT_BY_LOCATION)) {
            return new FindAirportByLocationCommand(airportDao);
        } else if (option.equals(GET_AIRPORT_NUMBERS_CACHE)) {
            return new GetAirportNumbersCacheCommand(airportDao);
        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }

    public Command createFlightCommand(MenuOptions.FlightMenuOptions option) {
        if (option.equals(FIND_ALL_FLIGHTS)) {
            return new FindAllFlightsCommand(flightDao);
        } else if (option.equals(FIND_FLIGHT_BY_NUMBER)) {
            return new FindFlightByNumberCommand(flightDao);
        } else if (option.equals(DELETE_FLIGHT_BY_NUMBER)) {
            return new DeleteFlightByNumberCommand(flightDao, bookingDao);
        } else if (option.equals(INSERT_FLIGHT)) {
            return new InsertFlightCommand(flightDao);
        } else if (option.equals(FILTER_FLIGHT_BY_AIRLINE_NAME)) {
            return new FilterFlightByAirlineNameCommand(flightDao);
        } else if (option.equals(FILTER_FLIGHT_BY_DEPARTURE_TIME)) {
            return new FilterFlightByDepartureTimeCommand(flightDao);
        } else if (option.equals(FIND_FLIGHT_BY_AIRLINE_NAME)) {
            return new FindFlightByAirlineNameCommand(flightDao);
        }else if (option.equals(GET_FLIGHT_NUMBERS_CACHE)) {
            return new GetFlightNumbersCacheCommand(flightDao);

        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }

    public Command createBookingCommand(MenuOptions.BookingMenuOptions option) {
        if (option.equals(FIND_ALL_BOOKINGS)) {
            return new FindAllBookingsCommand(bookingDao);
        } else if (option.equals(FIND_BOOKING_BY_NUMBER)) {
            return new FindBookingByNumberCommand(bookingDao);
        } else if (option.equals(DELETE_BOOKING_BY_NUMBER)) {
            return new DeleteBookingByNumberCommand(bookingDao, paymentDao);
        } else if (option.equals(INSERT_BOOKING)) {
            return new InsertBookingCommand(bookingDao);
        } else if (option.equals(GET_BOOKING_NUMBERS_CACHE)) {
            return new GetBookingNumbersCacheCommand(bookingDao);
        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }

    public Command createPaymentCommand(MenuOptions.PaymentMenuOptions option) {
        if (option.equals(FIND_ALL_PAYMENTS)) {
            return new FindAllPaymentsCommand(paymentDao);
        } else if (option.equals(FIND_PAYMENT_BY_NUMBER)) {
            return new FindPaymentByNumberCommand(paymentDao);
        } else if (option.equals(DELETE_PAYMENT_BY_NUMBER)) {
            return new DeletePaymentByNumberCommand(paymentDao);
        } else if (option.equals(INSERT_PAYMENT)) {
            return new InsertPaymentCommand(paymentDao);
        } else if (option.equals(FILTER_PAYMENT_BY_PAYMENT_METHOD)) {
            return new FilterPaymentByPaymentMethodCommand(paymentDao);
        } else if (option.equals(FIND_PAYMENT_BY_PAYMENT_METHOD)) {
            return new FindPaymentByPaymentMethodCommand(paymentDao);
        } else if (option.equals(GET_PAYMENT_NUMBERS_CACHE)) {
            return new GetPaymentNumbersCacheCommand(paymentDao);
        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }

    public Command createCustomerBookingFlightCommand(MenuOptions.CustomerMenuOptions option) {
        if(option.equals(FIND_ALL_CUSTOMERS_BOOKINGS_FLIGHTS)){
            return new FindRelatedBookingWithFlightForCustomerCommand(customerBookingDao);
        }
        throw new IllegalArgumentException("Invalid option: " + option);
    }
}