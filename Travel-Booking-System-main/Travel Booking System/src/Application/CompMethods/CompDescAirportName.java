package Application.CompMethods;

import Application.DTOs.Airport;

import java.util.Comparator;

public class CompDescAirportName implements Comparator<Airport> {

    //compare airports on descending airport nunmber and if the same then with airport name descending
    @Override
    public int compare(Airport o1, Airport o2) {
        int x = o2.getAirport_number().compareTo(o1.getAirport_number());
        if(x == 0){
            return o2.getAirport_name().compareTo(o1.getAirport_name());
        }
        else{
            return x;
        }
    }

}
