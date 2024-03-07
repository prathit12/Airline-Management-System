package Application.DTOs;

//this object was made for the implementation of my join query
//where it finds all the customers with their bookings and flights
public class CustomerBooking {
    private Customer customer;
    private Booking booking;
    private Flight flight;

    public CustomerBooking(Customer customer, Booking booking, Flight flight){
        this.customer = customer;
        this.booking = booking;
        this.flight = flight;
    }

    public Customer getCustomer(){
        return customer;
    }

    public Booking getBooking(){
        return booking;
    }

    public Flight getFlight(){
        return flight;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public void setBooking(Booking booking){
        this.booking = booking;
    }

    public void setFlight(Flight flight){
        this.flight = flight;
    }

    @Override
    public String toString(){
        return "Customer" + customer.getCustomer_number() + "- " + customer.getCustomer_name() + " with a Booking Number of " + booking.getBooking_number() + " for Flight Number " + flight.getFlight_number() + " on " + booking.getTravel_date() + " in Seat Number " + booking.getSeat_number() + ".";
    }
}
