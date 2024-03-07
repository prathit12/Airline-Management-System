package Application.Server.FlightCommands;

import Application.DAOs.FlightDaoInterface;
import Application.DTOs.Flight;
import Application.Exceptions.DaoException;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
public class FilterFlightByAirlineNameCommand implements Command{
    private FlightDaoInterface flightDao;
    public FilterFlightByAirlineNameCommand(FlightDaoInterface flightDao) {
        this.flightDao = flightDao;
    }
    @Override
    public Packet execute(Object data) {
        try {
            Set<String> uniqueAirlineNames = flightDao.uniqueAirlineName();
            HashMap<Integer, String> numberedAirlineNames = new HashMap<>();
            int i = 1;
            for (String airlineName : uniqueAirlineNames) {
                numberedAirlineNames.put(i, airlineName);
                i++;
            }
            Gson gson = new Gson();
            String jsonNumberedAirlineNames = gson.toJson(numberedAirlineNames);
            return new Packet(jsonNumberedAirlineNames);
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}