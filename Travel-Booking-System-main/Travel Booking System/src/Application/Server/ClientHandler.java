package Application.Server;

import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;

import java.io.*;
import java.net.Socket;

import static Application.Protocol.MenuOptions.AirportMenuOptions.*;
import static Application.Protocol.MenuOptions.CustomerMenuOptions.*;
import static Application.Protocol.MenuOptions.FlightMenuOptions.*;
import static Application.Protocol.MenuOptions.BookingMenuOptions.*;
import static Application.Protocol.MenuOptions.PaymentMenuOptions.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private int clientNumber;

    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            while (true) {
                String input = in.readLine();

                if (input == null) {
                    System.out.println("Server: Client " + clientNumber + " disconnected.");
                    clientSocket.close();
                    break;
                }

                Packet packet = Packet.fromJson(input);
                CommandFactory factory = new CommandFactory();

                Command command = null;
                Object data = packet.getData();

                Enum option = packet.getOption();

                if (option.equals(FIND_ALL_CUSTOMERS) || option.equals(FIND_CUSTOMER_BY_NUMBER) || option.equals(DELETE_CUSTOMER_BY_NUMBER) || option.equals(INSERT_CUSTOMER) || option.equals(CHECK_DUPLICATE_EMAIL) || option.equals(GET_CUSTOMER_NUMBERS_CACHE)) {
                    command = factory.createCustomerCommand((MenuOptions.CustomerMenuOptions) option);
                } else if (option.equals(FIND_ALL_AIRPORTS) || option.equals(FIND_AIRPORT_BY_NUMBER) || option.equals(DELETE_AIRPORT_BY_NUMBER) || option.equals(INSERT_AIRPORT) || option.equals(FILTER_AIRPORT_BY_CITY) || option.equals(FIND_AIRPORT_BY_LOCATION) || option.equals(GET_AIRPORT_NUMBERS_CACHE)) {
                    command = factory.createAirportCommand((MenuOptions.AirportMenuOptions) option);
                }
                else if (option.equals(FIND_ALL_FLIGHTS) || option.equals(FIND_FLIGHT_BY_NUMBER) || option.equals(DELETE_FLIGHT_BY_NUMBER) || option.equals(INSERT_FLIGHT) || option.equals(FILTER_FLIGHT_BY_AIRLINE_NAME) || option.equals(FILTER_FLIGHT_BY_DEPARTURE_TIME) || option.equals(FIND_FLIGHT_BY_AIRLINE_NAME) || option.equals(GET_FLIGHT_NUMBERS_CACHE)) {
                    command = factory.createFlightCommand((MenuOptions.FlightMenuOptions) option);
                }
                else if (option.equals(FIND_ALL_BOOKINGS) || option.equals(FIND_BOOKING_BY_NUMBER) || option.equals(DELETE_BOOKING_BY_NUMBER) || option.equals(INSERT_BOOKING) || option.equals(GET_BOOKING_NUMBERS_CACHE)) {
                    command = factory.createBookingCommand((MenuOptions.BookingMenuOptions) option);
                }
                else if (option.equals(FIND_ALL_PAYMENTS) || option.equals(FIND_PAYMENT_BY_NUMBER) || option.equals(DELETE_PAYMENT_BY_NUMBER) || option.equals(INSERT_PAYMENT) || option.equals(FILTER_PAYMENT_BY_PAYMENT_METHOD) || option.equals(FIND_PAYMENT_BY_PAYMENT_METHOD) || option.equals(GET_PAYMENT_NUMBERS_CACHE)) {
                    command = factory.createPaymentCommand((MenuOptions.PaymentMenuOptions) option);
                }else if(option.equals(FIND_ALL_CUSTOMERS_BOOKINGS_FLIGHTS)){
                    command = factory.createCustomerBookingFlightCommand((MenuOptions.CustomerMenuOptions) option);
                }

                if (command != null) {
                    Packet responsePacket = command.execute(data);
                    out.println(responsePacket.toJson());
                }
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e.getMessage());
        }
    }
}
