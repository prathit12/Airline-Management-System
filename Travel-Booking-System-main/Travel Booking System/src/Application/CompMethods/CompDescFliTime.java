package Application.CompMethods;

import Application.DTOs.Flight;

import java.util.Comparator;

public class CompDescFliTime implements Comparator<Flight> {

    //compare flights on descending flight time and if the same then with flight cost
    @Override
    public int compare(Flight o1, Flight o2) {
        int x = o2.getDeparture_time().compareTo(o1.getDeparture_time());
        if(x == 0){
            //flight_cost is a double so we need to cast it to a Double
            return ((Double)o2.getFlight_cost()).compareTo(o1.getFlight_cost());
        }
        else{
            return x;
        }
    }
}
