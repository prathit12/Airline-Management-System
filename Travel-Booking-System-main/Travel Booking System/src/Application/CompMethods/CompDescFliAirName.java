package Application.CompMethods;

import Application.DTOs.Flight;

import java.util.Comparator;

public class CompDescFliAirName implements Comparator<Flight> {

    //compare flights on descending airline name and if the same then with flight cost
    @Override
    public int compare(Flight o1, Flight o2) {
        int x = o2.getAirline_name().compareTo(o1.getAirline_name());
        if(x == 0){
            //flight_cost is a double so we need to cast it to a Double
            return ((Double)o2.getFlight_cost()).compareTo(o1.getFlight_cost());
        }
        else{
            return x;
        }
    }
}
