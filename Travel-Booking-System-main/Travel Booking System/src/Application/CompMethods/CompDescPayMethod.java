package Application.CompMethods;

import Application.DTOs.Payment;

import java.util.Comparator;

public class CompDescPayMethod implements Comparator<Payment> {
    //compare payment with prices in descending order and if the same price, compare by payment_date
    @Override
    public int compare(Payment p1, Payment p2) {
        int x = ((Double)p2.getAmount_paid()).compareTo(p1.getAmount_paid());
        if(x == 0){
            return p2.getPayment_date().compareTo(p1.getPayment_date());
        }
        else{
            return x;
        }
    }
}
