package Application.DTOs;

import java.util.Objects;

public class Airport implements Comparable<Airport> {
    //-- Create Airport table
    //CREATE TABLE airport (
    //    airport_id INT PRIMARY KEY AUTO_INCREMENT,
    //    airport_name VARCHAR(30),
    //    airport_location VARCHAR(50)
    //);
    //the airport has the following fields: airport id - primary key and auto increment, airport name and airport_location

    private String airport_number; // primary key

    private int airport_id;

    private static int airport_id_counter = 1;
    private String airport_name;
    private String airport_location;

    //default constructor
    public Airport() {
    }

    //constructor with parameters
    public Airport(String airport_number, String airport_name, String airport_location) {
        this.airport_number = airport_number;
        this.airport_id = ++airport_id_counter;
        this.airport_name = airport_name;
        this.airport_location = airport_location;
    }

    //parameter with airport_id
    public Airport(int airport_id, String airport_number, String airport_name, String airport_location) {
        this.airport_number = airport_number;
        this.airport_id = airport_id;
        this.airport_name = airport_name;
        this.airport_location = airport_location;
    }

    //getters and setters
    public String getAirport_number() {
        return airport_number;
    }

    public void setAirport_number(String airport_number) {
        this.airport_number = airport_number;
    }

    public int getAirport_id() {
        return airport_id;
    }

    public void setAirport_id(int airport_id) {
        this.airport_id = airport_id;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getAirport_location() {
        return airport_location;
    }

    public void setAirport_location(String airport_location) {
        this.airport_location = airport_location;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airport_number='" + airport_number + '\'' +
                ", airport_id=" + airport_id +
                ", airport_name='" + airport_name + '\'' +
                ", airport_location='" + airport_location + '\'' +
                '}';
    }

    //compare to method based on airport_number
    @Override
    public int compareTo(Airport o) {
        //based on id
        return this.getAirport_number().compareTo(o.getAirport_number());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport airport)) return false;
        return Objects.equals(getAirport_number(), airport.getAirport_number()) && Objects.equals(getAirport_name(), airport.getAirport_name()) && Objects.equals(getAirport_location(), airport.getAirport_location());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAirport_number(), getAirport_name(), getAirport_location());
    }
}
