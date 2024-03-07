package Application.DTOs;

import java.util.Objects;

public class Payment implements Comparable<Payment> {
    //-- Create Payment table
    //CREATE TABLE Payment (
    //    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    //    booking_id INT NOT NULL,
    //    amount_paid DECIMAL(10, 2),
    //    payment_date DATE,
    //    method VARCHAR(20),
    //    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
    //);

    private String payment_number; // primary key
    private int payment_id;
    private static int payment_id_counter = 0;
    private String booking_number;
    private double amount_paid;
    private String payment_date;
    private String method;

    //default constructor
    public Payment() {
    }

    //constructor with parameters
    public Payment(String payment_number, String booking_number, double amount_paid, String payment_date, String method) {
        this.payment_number = payment_number;
        this.payment_id = ++payment_id_counter;
        this.booking_number = booking_number;
        this.amount_paid = amount_paid;
        this.payment_date = payment_date;
        this.method = method;
    }

    //parameter with payment_id
    public Payment(int payment_id, String payment_number, String booking_number, double amount_paid, String payment_date, String method) {
        this.payment_number = payment_number;
        this.payment_id = payment_id;
        this.booking_number = booking_number;
        this.amount_paid = amount_paid;
        this.payment_date = payment_date;
        this.method = method;
    }

    //getters and setters
    public String getPayment_number() {
        return payment_number;
    }

    public void setPayment_number(String payment_number) {
        this.payment_number = payment_number;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getBooking_number() {
        return booking_number;
    }

    public void setBooking_number(String booking_number) {
        this.booking_number = booking_number;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(double amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payment_number='" + payment_number + '\'' +
                ", payment_id=" + payment_id +
                ", booking_number='" + booking_number + '\'' +
                ", amount_paid=" + amount_paid +
                ", payment_date='" + payment_date + '\'' +
                ", method='" + method + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return getPayment_id() == payment.getPayment_id() && Double.compare(payment.getAmount_paid(), getAmount_paid()) == 0 && Objects.equals(getBooking_number(), payment.getBooking_number()) && Objects.equals(getPayment_date(), payment.getPayment_date()) && Objects.equals(getMethod(), payment.getMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPayment_id(), getBooking_number(), getAmount_paid(), getPayment_date(), getMethod());
    }

    @Override
    public int compareTo(Payment o) {
        return this.payment_number.compareTo(o.payment_number);
    }
}
