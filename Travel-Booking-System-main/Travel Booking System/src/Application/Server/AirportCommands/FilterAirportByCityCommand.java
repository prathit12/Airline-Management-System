package Application.Server.AirportCommands;

import Application.DAOs.AirportDaoInterface;
import Application.DTOs.Airport;
import Application.Exceptions.DaoException;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FilterAirportByCityCommand implements Command {
    private AirportDaoInterface airportDao;

    public FilterAirportByCityCommand(AirportDaoInterface airportDao) {
        this.airportDao = airportDao;
    }

    @Override
    public Packet execute(Object data) {
        try {
            Set<String> uniqueAirportLocations = airportDao.uniqueAirportLocation();
            HashMap<Integer, String> numberedAirportLocations = new HashMap<>();
            int i = 1;
            for (String airportLocation : uniqueAirportLocations) {
                numberedAirportLocations.put(i, airportLocation);
                i++;
            }
            Gson gson = new Gson();
            String jsonNumberedAirportLocations = gson.toJson(numberedAirportLocations);
            return new Packet(jsonNumberedAirportLocations);
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}