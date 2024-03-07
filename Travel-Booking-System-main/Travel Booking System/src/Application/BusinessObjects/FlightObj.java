package Application.BusinessObjects;

import Application.CompMethods.CompAscFliAirName;
import Application.CompMethods.CompAscFliTime;
import Application.CompMethods.CompDescFliAirName;
import Application.CompMethods.CompDescFliTime;
import Application.DAOs.*;
import Application.DTOs.*;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.*;

public class FlightObj {
    //this is the helper class to validate user input
    static Helpers helper;
    static FlightDaoInterface flightDao = new MySqlFlightDao();
    static BookingDaoInterface bookingDao = new MySqlBookingDao();
    //this is the cache for flight numbers
    private TreeSet<String> flightNumbersCache;


    private Scanner input;
    private PrintWriter output;

    public FlightObj(Scanner input, PrintWriter output) {
        this.input = input;
        this.output = output;
        flightNumbersCache = new TreeSet<>();
    }

    //this method will fetch the flight numbers cache from the server
    public void fetchFlightNumbersCache() {
        //first we need to clear the cache
        flightNumbersCache.clear();
        Packet request = new Packet(MenuOptions.FlightMenuOptions.GET_FLIGHT_NUMBERS_CACHE, null);
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
            flightNumbersCache = new Gson().fromJson(jsonCache, cacheType);
        }
    }

    //display all flights
    public void findAllFlights() {
        Packet request = new Packet(MenuOptions.FlightMenuOptions.FIND_ALL_FLIGHTS, null);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
        } else {
            String jsonFlights = (String) response.getData();
            Type flightListType = new TypeToken<List<Flight>>() {
            }.getType();
            List<Flight> flights = new Gson().fromJson(jsonFlights, flightListType);

            if (flights.isEmpty()) {
                System.out.println("No flights found.");
            }
            for (Flight flight : flights) {
                System.out.println(flight.toString());
            }
        }
    }

    //to find flight by number and also if no flight found then it will display no flight found
    //and if flight found then it will display flight details(flightNumber is a String)
    public void findFlightByNumber() {
        helper = new Helpers(input, output);
        String flightNumber = helper.readString("Enter flight number: ");
        if(!flightNumbersCache.contains(flightNumber.toLowerCase()) && !flightNumbersCache.contains(flightNumber.toUpperCase())) {
            System.out.println("Customer number not found.");
            return;
        }
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
                System.out.println("No flight found.");
            } else {
                System.out.println(flight);
            }
        }
    }

    //to delete flight by number and also if no flight found then it will display no flight found
    //and also check if flight has related records in booking table then it will display those records
    public void deleteFlightByNumber() {
        helper = new Helpers(input, output);
        String flightNumber = helper.readString("Enter flight number: ");
        if(!flightNumbersCache.contains(flightNumber.toLowerCase()) && !flightNumbersCache.contains(flightNumber.toUpperCase())) {
            System.out.println("Customer number not found.");
            return;
        }
        Packet request = new Packet(MenuOptions.FlightMenuOptions.DELETE_FLIGHT_BY_NUMBER, flightNumber);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            String exceptionMessage = response.getException().getMessage();
//            System.out.println("Error: " + (exceptionMessage == null ? "Unknown error" : exceptionMessage));

            if (response.getData() != null) {
                System.out.println("Flight-" + flightNumber + " cannot be deleted because it has related records in the database:");

                // The related bookings can be sent as part of the response in the 'data' field.
                String jsonBookings = (String) response.getData();
                Type bookingListType = new TypeToken<List<Booking>>() {
                }.getType();
                List<Booking> bookings = new Gson().fromJson(jsonBookings, bookingListType);

                for (Booking booking : bookings) {
                    System.out.println(booking);
                }
            }
        } else {
            boolean deleted = Boolean.parseBoolean((String) response.getData());
            if (deleted) {
                System.out.println("Flight deleted.");
                flightNumbersCache.remove(flightNumber.toLowerCase());
            } else {
                System.out.println("No flight found.");
            }
        }
    }

    //to insert a new flight
    public void insertFlight() {
        helper = new Helpers(input, output);
        String flightNumber = helper.readInputField("flightNumber", 10);
        String airportNumber = helper.checkAirportNumber();
        String departureLocation = helper.readInputField("departureLocation", 50);
        String departureTime = helper.readTime();
        String arrivalLocation = helper.readInputField("arrivalLocation", 50);
        String arrivalTime = helper.readTime();
        String airlineName = helper.readInputField("airlineName", 30);
        double flightCost = helper.readFlightCost();

        Flight flight = new Flight(flightNumber, airportNumber, departureLocation, departureTime, arrivalLocation, arrivalTime, airlineName, flightCost);
        Packet request = new Packet(MenuOptions.FlightMenuOptions.INSERT_FLIGHT, flight);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error inserting flight: " + response.getException().getMessage());
        } else {
            String jsonFlight = (String) response.getData();
            Flight f = new Gson().fromJson(jsonFlight, Flight.class);
            if (f == null) {
                System.out.println("Flight was not inserted.");
            } else {
                System.out.println(f);
                System.out.println("Flight inserted.");
                flightNumbersCache.add(flight.getFlight_number().toLowerCase());
            }
        }
    }

    //to filter flights by airline
    public void filterFlightByAirline() {
        helper = new Helpers(input, output);
        Packet request = new Packet(MenuOptions.FlightMenuOptions.FILTER_FLIGHT_BY_AIRLINE_NAME, null);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
        } else {
            // Deserialize the HashMap received from the server
            Type hashMapType = new TypeToken<HashMap<Integer, String>>() {
            }.getType();
            HashMap<Integer, String> numberedAirlineNames = new Gson().fromJson((String) response.getData(), hashMapType);

            for (Map.Entry<Integer, String> entry : numberedAirlineNames.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }
            while (true) {
                int choice = helper.readInt("Enter your choice: ");
                if (choice > numberedAirlineNames.size() || choice < 1) {
                    System.out.println("Invalid choice, please try again.");
                } else {
                    String airlineName = numberedAirlineNames.get(choice);
                    Packet airlineNameRequest = new Packet(MenuOptions.FlightMenuOptions.FIND_FLIGHT_BY_AIRLINE_NAME, airlineName);
                    String jsonAirlineNameRequest = airlineNameRequest.toJson();
                    output.println(jsonAirlineNameRequest);
                    output.flush();

                    String jsonAirlineNameResponse = input.nextLine();
//                    System.out.println(jsonAirlineNameResponse); //Debugging line
                    Packet airlineNameResponse = Packet.fromJson(jsonAirlineNameResponse);

                    if (airlineNameResponse.getException() != null) {
                        System.out.println("Error: " + airlineNameResponse.getException().getMessage());
                    } else {
                        String jsonFlights = (String) airlineNameResponse.getData();
                        Type flightListType = new TypeToken<List<Flight>>() {
                        }.getType();
                        List<Flight> flights = new Gson().fromJson(jsonFlights, flightListType);

                        if (flights.isEmpty()) {
                            System.out.println("No flights found.");
                        } else {
                            System.out.println("How do you want the flights to be sorted?");
                            System.out.println("1. By Default");
                            System.out.println("2. By Ascending Order");
                            System.out.println("3. By Descending Order");
                            while (true) {
                                int sortChoice = helper.readInt("Enter your choice: ");
                                if (sortChoice > 3 || sortChoice < 1) {
                                    System.out.println("Invalid choice, please try again.");
                                } else {
                                    switch (sortChoice) {
                                        case 1:
                                            System.out.println("Flights by " + airlineName + ":");
                                            for (Flight flight : flights) {
                                                System.out.println(flight);
                                            }
                                            break;
                                        case 2:
                                            System.out.println("Flights by " + airlineName + " sorted by ascending order:");
                                            flights.sort(new CompAscFliAirName());
                                            for (Flight flight : flights) {
                                                System.out.println(flight);
                                            }
                                            break;
                                        case 3:
                                            System.out.println("Flights by " + airlineName + " sorted by descending order:");
                                            flights.sort(new CompDescFliAirName());
                                            for (Flight flight : flights) {
                                                System.out.println(flight);
                                            }
                                            break;
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    //to filter flights by departure time
    public void filterFlightByDepartureTime() {
        helper = new Helpers(input, output);

        Packet request = new Packet(MenuOptions.FlightMenuOptions.FILTER_FLIGHT_BY_DEPARTURE_TIME, null);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
        } else {
            // Deserialize the HashMap received from the server
            Type hashMapType = new TypeToken<HashMap<String, List<Flight>>>() {
            }.getType();
            HashMap<String, List<Flight>> flightsByDepartureTime = new Gson().fromJson((String) response.getData(), hashMapType);

            int i = 1;
            for (Map.Entry<String, List<Flight>> entry : flightsByDepartureTime.entrySet()) {
                System.out.println(i + ". " + entry.getKey());
                i++;
            }
            while (true) {
                int choice = helper.readInt("Enter your choice: ");
                if (choice > flightsByDepartureTime.size() || choice < 1) {
                    System.out.println("Invalid choice, please try again.");
                } else {
                    String departureTime = new ArrayList<>(flightsByDepartureTime.keySet()).get(choice - 1);
                    //the flights is the value of the map with the key of departureTime
                    List<Flight> flights = flightsByDepartureTime.get(departureTime);
                    if (flights.isEmpty()) {
                        System.out.println("No flights found.");
                    } else {
                        System.out.println("How do you want the flights to be sorted?");
                        System.out.println("1. By Default");
                        System.out.println("2. By Ascending Order");
                        System.out.println("3. By Descending Order");
                        while (true) {
                            int sortChoice = helper.readInt("Enter your choice: ");
                            if (sortChoice > 3 || sortChoice < 1) {
                                System.out.println("Invalid choice, please try again.");
                            } else {
                                switch (sortChoice) {
                                    case 1:
                                        System.out.println("Flights by " + departureTime + ":");
                                        for (Flight flight : flights) {
                                            System.out.println(flight);
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Flights by " + departureTime + " sorted by ascending order:");
                                        flights.sort(new CompAscFliTime());
                                        for (Flight flight : flights) {
                                            System.out.println(flight);
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Flights by " + departureTime + " sorted by descending order:");
                                        flights.sort(new CompDescFliTime());
                                        for (Flight flight : flights) {
                                            System.out.println(flight);
                                        }
                                        break;
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }
}
