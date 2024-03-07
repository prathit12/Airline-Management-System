package Application.BusinessObjects;

import Application.CompMethods.CompAscAirportName;
import Application.CompMethods.CompDescAirportName;
import Application.DAOs.*;
import Application.DTOs.*;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;

public class AirportObj {
    //this helper is used to validate user input
    static Helpers helper;
    static AirportDaoInterface airportDao = new MySqlAirportDao();
    static FlightDaoInterface flightDao = new MySqlFlightDao();

    //to store airport numbers in cache and use them to dp check before querying the database
    private TreeSet<String> airportNumbersCache;


    private Scanner input;
    private PrintWriter output;

    public AirportObj(Scanner input, PrintWriter output) {
        this.input = input;
        this.output = output;
        airportNumbersCache = new TreeSet<>();
    }


    //to fill in the cache
    public void fetchAirportNumbersCache() {
        //first we need to clear the cache
        airportNumbersCache.clear();
        Packet request = new Packet(MenuOptions.AirportMenuOptions.GET_AIRPORT_NUMBERS_CACHE, null);
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
            airportNumbersCache = new Gson().fromJson(jsonCache, cacheType);
        }
    }

    //display all airports
    public void findAllAirports() {
        Packet request = new Packet(MenuOptions.AirportMenuOptions.FIND_ALL_AIRPORTS, null);
                String jsonRequest = request.toJson();
                output.println(jsonRequest);
                output.flush();

                String jsonResponse = input.nextLine();
                Packet response = Packet.fromJson(jsonResponse);

                if (response.getException() != null) {
                    System.out.println("Error: " + response.getException().getMessage());
                } else {
                    String jsonAirports = (String) response.getData();
                    Type airportListType = new TypeToken<List<Airport>>(){}.getType();
                    List<Airport> airports = new Gson().fromJson(jsonAirports, airportListType);

                    if (airports.isEmpty()) {
                        System.out.println("No airports found.");
                    }
                    for (Airport airport : airports) {
                        System.out.println(airport.toString());
                    }
                }
    }

    //to find airport by number and also if no airport found then it will display no airport found
    //and if airport found then it will display airport details(airportNumber is a String)
    public void findAirportByNumber() {
        helper = new Helpers(input, output);
        String airportNumber = helper.readString("Enter airport number: ");
        if(!airportNumbersCache.contains(airportNumber.toLowerCase()) && !airportNumbersCache.contains(airportNumber.toUpperCase())) {
            System.out.println("Airport number not found.");
            return;
        }
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
                System.out.println("No airport found.");
            } else {
                System.out.println(airport);
            }
        }
    }

    //to delete airport by number and also if no airport found then it will display no airport found
    public void deleteAirportByNumber() {
        helper = new Helpers(input, output);
        String airportNumber = helper.readString("Enter airport number: ");
        if(!airportNumbersCache.contains(airportNumber.toLowerCase()) && !airportNumbersCache.contains(airportNumber.toUpperCase())) {
            System.out.println("Airport number not found.");
            return;
        }
        Packet request = new Packet(MenuOptions.AirportMenuOptions.DELETE_AIRPORT_BY_NUMBER, airportNumber);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        //        System.out.println("Client: Received JSON: " + jsonResponse); // Debugging line
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Error: " + response.getException().getMessage());
            //            System.out.println("Error: " + (exceptionMessage == null ? "Unknown error" : exceptionMessage));

            if(response.getData() != null) {
                System.out.println("Airport-" + airportNumber + " cannot be deleted because it has related records in the database:");

                // The related flights can be sent as part of the response in the 'data' field.
                String jsonFlights = (String) response.getData();
                Type flightListType = new TypeToken<List<Flight>>(){}.getType();
                List<Flight> flights = new Gson().fromJson(jsonFlights, flightListType);

                for (Flight flight : flights) {
                    System.out.println(flight);
                }
            }
        } else {
            boolean deleted = Boolean.parseBoolean((String) response.getData());
            if (deleted) {
                System.out.println("Airport deleted.");
                airportNumbersCache.remove(airportNumber.toLowerCase());
            } else {
                System.out.println("No airport found.");
            }
        }
    }

    //to insert a new airport
    public void insertAirport() {
        helper = new Helpers(input, output);
        String airportNumber = helper.readString("Enter airport number: ");
        String airportName = helper.readString("Enter airport name: ");
        String airportLocation = helper.readString("Enter airport location: ");
        Airport airport = new Airport(airportNumber, airportName, airportLocation);
        Packet request = new Packet(MenuOptions.AirportMenuOptions.INSERT_AIRPORT, airport);
        String jsonRequest = request.toJson();
        output.println(jsonRequest);
        output.flush();

        String jsonResponse = input.nextLine();
        Packet response = Packet.fromJson(jsonResponse);

        if (response.getException() != null) {
            System.out.println("Client: Error inserting airport: " + response.getExceptionMessage()); // Debugging line
        } else {
            String jsonAirport = (String) response.getData();
            Airport a = new Gson().fromJson(jsonAirport, Airport.class);
            if (a == null) {
                System.out.println("Airport was not inserted.");
            } else {
                System.out.println(a);
                System.out.println("Airport inserted.");
                airportNumbersCache.add(airport.getAirport_number().toLowerCase());
            }
        }
    }

    //to filter the airports by location
    public void filterAirportByCity() {
        helper = new Helpers(input, output);
        Packet request = new Packet(MenuOptions.AirportMenuOptions.FILTER_AIRPORT_BY_CITY, null);
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
            HashMap<Integer, String> numberedAirportLocations = new Gson().fromJson((String) response.getData(), hashMapType);

            for (Map.Entry<Integer, String> entry : numberedAirportLocations.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }
            while (true) {
                int choice = helper.readInt("Enter your choice: ");
                if (choice > numberedAirportLocations.size() || choice < 1) {
                    System.out.println("Invalid choice, please try again.");
                } else {
                    String airportLocation = numberedAirportLocations.get(choice);

                    Packet locationRequest = new Packet(MenuOptions.AirportMenuOptions.FIND_AIRPORT_BY_LOCATION, airportLocation);
                    String jsonLocationRequest = locationRequest.toJson();
                    output.println(jsonLocationRequest);
                    output.flush();

                    String jsonLocationResponse = input.nextLine();
                    Packet locationResponse = Packet.fromJson(jsonLocationResponse);

                    if (locationResponse.getException() != null) {
                        System.out.println("Error: " + locationResponse.getException().getMessage());
                    } else {
                        String jsonAirports = (String) locationResponse.getData();
                        Type airportListType = new TypeToken<List<Airport>>(){}.getType();
                        List<Airport> airports = new Gson().fromJson(jsonAirports, airportListType);

                        if (airports.isEmpty()) {
                            System.out.println("No airports found.");
                        } else {
                            System.out.println("How do you want the airports to be sorted?");
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
                                            System.out.println("Airports in " + airportLocation + ":");
                                            for (Airport airport : airports) {
                                                System.out.println(airport);
                                            }
                                            break;
                                        case 2:
                                            System.out.println("Airports in " + airportLocation + " sorted by ascending order:");
                                            airports.sort(new CompAscAirportName());
                                            for (Airport airport : airports) {
                                                System.out.println(airport);
                                            }
                                            break;
                                        case 3:
                                            System.out.println("Airports in " + airportLocation + " sorted by descending order:");
                                            airports.sort(new CompDescAirportName());
                                            for (Airport airport : airports) {
                                                System.out.println(airport);
                                            }
                                            break;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }
}
