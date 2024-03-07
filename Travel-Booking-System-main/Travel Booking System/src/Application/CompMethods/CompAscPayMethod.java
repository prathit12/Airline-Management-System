package Application.CompMethods;

import Application.DTOs.Payment;

import java.util.Comparator;

public class CompAscPayMethod implements Comparator<Payment> {
    //compare payment with prices in ascending order and if the same price, compare by payment_date
    @Override
    public int compare(Payment p1, Payment p2) {
        int x = ((Double)p1.getAmount_paid()).compareTo(p2.getAmount_paid());
        if(x == 0){
            return p1.getPayment_date().compareTo(p2.getPayment_date());
        }
        else{
            return x;
        }
    }
}
