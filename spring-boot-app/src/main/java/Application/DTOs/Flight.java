package Application.DTOs;

import java.util.Objects;

public class Flight implements Comparable<Flight> {
    //-- Create Flight table
    //CREATE TABLE flight (
    //    flight_id INT PRIMARY KEY AUTO_INCREMENT,
    //    airport_id INT NOT NULL,
    //    departure_location VARCHAR(50),
    //    departure_time TIME,
    //    arrival_location VARCHAR(50),
    //    arrival_time TIME,
    //    airline_name VARCHAR(30),
    //    flight_cost DECIMAL(10, 2),
    //    FOREIGN KEY (airport_id) REFERENCES airport(airport_id)
    //);

    private String flight_number; // primary key
    private int flight_id;
    private static int flight_id_counter = 0;
    private String airport_number;
    private String departure_location;

    private String departure_time;
    private String arrival_location;

    private String arrival_time;
    private String airline_name;
    private double flight_cost;

    //default constructor
    public Flight() {
    }

    //constructor with parameters
    public Flight(String flight_number, String airport_number, String departure_location, String departure_time, String arrival_location, String arrival_time, String airline_name, double flight_cost) {
        this.flight_number = flight_number;
        this.flight_id = ++flight_id_counter;
        this.airport_number = airport_number;
        this.departure_location = departure_location;
        this.departure_time = departure_time;
        this.arrival_location = arrival_location;
        this.arrival_time = arrival_time;
        this.airline_name = airline_name;
        this.flight_cost = flight_cost;
    }

    //parameter with flight_id
    public Flight(int flight_id, String flight_number, String airport_number, String departure_location, String departure_time, String arrival_location, String arrival_time, String airline_name, double flight_cost) {
        this.flight_number = flight_number;
        this.flight_id = flight_id;
        this.airport_number = airport_number;
        this.departure_location = departure_location;
        this.departure_time = departure_time;
        this.arrival_location = arrival_location;
        this.arrival_time = arrival_time;
        this.airline_name = airline_name;
        this.flight_cost = flight_cost;
    }

    //getters and setters
    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getAirport_number() {
        return airport_number;
    }

    public void setAirport_number(String airport_number) {
        this.airport_number = airport_number;
    }

    public String getDeparture_location() {
        return departure_location;
    }

    public void setDeparture_location(String departure_location) {
        this.departure_location = departure_location;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_location() {
        return arrival_location;
    }

    public void setArrival_location(String arrival_location) {
        this.arrival_location = arrival_location;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getAirline_name() {
        return airline_name;
    }

    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
    }

    public double getFlight_cost() {
        return flight_cost;
    }

    public void setFlight_cost(double flight_cost) {
        this.flight_cost = flight_cost;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flight_number='" + flight_number + '\'' +
                ", flight_id=" + flight_id +
                ", airport_number='" + airport_number + '\'' +
                ", departure_location='" + departure_location + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", arrival_location='" + arrival_location + '\'' +
                ", arrival_time='" + arrival_time + '\'' +
                ", airline_name='" + airline_name + '\'' +
                ", flight_cost=" + flight_cost +
                '}';
    }

    //compare flights on ascending flight number
    @Override
    public int compareTo(Flight o) {
        return this.flight_number.compareTo(o.flight_number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight flight)) return false;
        return Double.compare(flight.getFlight_cost(), getFlight_cost()) == 0 && Objects.equals(getFlight_number(), flight.getFlight_number()) && Objects.equals(getAirport_number(), flight.getAirport_number()) && Objects.equals(getDeparture_location(), flight.getDeparture_location()) && Objects.equals(getDeparture_time(), flight.getDeparture_time()) && Objects.equals(getArrival_location(), flight.getArrival_location()) && Objects.equals(getArrival_time(), flight.getArrival_time()) && Objects.equals(getAirline_name(), flight.getAirline_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlight_number(), getAirport_number(), getDeparture_location(), getDeparture_time(), getArrival_location(), getArrival_time(), getAirline_name(), getFlight_cost());
    }
}
