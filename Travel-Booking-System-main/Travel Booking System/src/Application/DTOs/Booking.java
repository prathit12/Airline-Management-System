package Application.DTOs;

public class Booking {
    //-- Create Booking table
    //CREATE TABLE booking (
    //    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    //    flight_id INT NOT NULL,
    //    customer_id INT NOT NULL,
    //    travel_date DATE NOT NULL,
    //    seats INT NOT NULL,
    //    FOREIGN KEY (flight_id) REFERENCES flight(flight_id),
    //    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
    //);

    private String booking_number; // primary key
    private int booking_id;
    private static int booking_id_counter = 0;
    private String flight_number;
    private String  customer_number;
    private String travel_date;
    private String seat_number;

    //default constructor
    public Booking() {
    }

    //constructor with parameters
    public Booking(String booking_number, String flight_number, String customer_number, String travel_date, String seat_number) {
        this.booking_number = booking_number;
        this.booking_id = ++booking_id_counter;
        this.flight_number = flight_number;
        this.customer_number = customer_number;
        this.travel_date = travel_date;
        this.seat_number = seat_number;
    }

    //parameter with booking_id
    public Booking(int booking_id, String booking_number, String flight_number, String customer_number, String travel_date, String seat_number) {
        this.booking_number = booking_number;
        this.booking_id = booking_id;
        this.flight_number = flight_number;
        this.customer_number = customer_number;
        this.travel_date = travel_date;
        this.seat_number = seat_number;
    }

    //getters and setters
    public String getBooking_number() {
        return booking_number;
    }

    public void setBooking_number(String booking_number) {
        this.booking_number = booking_number;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "booking_number='" + booking_number + '\'' +
                ", booking_id=" + booking_id +
                ", flight_number='" + flight_number + '\'' +
                ", customer_number='" + customer_number + '\'' +
                ", travel_date='" + travel_date + '\'' +
                ", seat_number='" + seat_number + '\'' +
                '}';
    }
}
