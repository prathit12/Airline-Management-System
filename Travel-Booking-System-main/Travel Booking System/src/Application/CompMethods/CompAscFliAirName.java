package Application.CompMethods;

import Application.DTOs.Flight;

import java.util.Comparator;

public class CompAscFliAirName implements Comparator<Flight> {

    //compare flights on ascending airline name and if the same then with flight cose
    @Override
    public int compare(Flight o1, Flight o2) {
        int x = o1.getAirline_name().compareTo(o2.getAirline_name());
        if(x == 0){
            //flight_cost is a double so we need to cast it to a Double
            return ((Double)o1.getFlight_cost()).compareTo(o2.getFlight_cost());
        }
        else{
            return x;
        }
    }

}
