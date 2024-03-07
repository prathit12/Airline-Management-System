package Application.CompMethods;

import Application.DTOs.Airport;

import java.util.Comparator;

public class CompAscAirportName implements Comparator<Airport> {

        //compare airports on ascending airport number and if the same then with airport name
        @Override
        public int compare(Airport o1, Airport o2) {
            int x = o1.getAirport_number().compareTo(o2.getAirport_number());
            if(x == 0){
                return o1.getAirport_name().compareTo(o2.getAirport_name());
            }
            else{
                return x;
            }
        }
}
