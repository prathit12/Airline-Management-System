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
import java.util.Map;
import java.util.Set;
public class FilterFlightByDepartureTimeCommand implements Command{
    private FlightDaoInterface flightDao;
    public FilterFlightByDepartureTimeCommand(FlightDaoInterface flightDao) {
        this.flightDao = flightDao;
    }
    @Override
    public Packet execute(Object data) {
        try {
            Map<String, List<Flight>> flightsByDepartureTime = flightDao.timeOfFlight();
            Gson gson = new Gson();
            String jsonFlightsByDepartureTime = gson.toJson(flightsByDepartureTime);
            return new Packet(jsonFlightsByDepartureTime);
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}
