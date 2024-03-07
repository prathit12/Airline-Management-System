package Application.Server.FlightCommands;

import Application.DAOs.FlightDaoInterface;
import Application.DTOs.Flight;
import Application.Exceptions.DaoException;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import java.util.List;
public class FindFlightByAirlineNameCommand implements Command{
    private FlightDaoInterface flightDao;
    public FindFlightByAirlineNameCommand(FlightDaoInterface flightDao) {
        this.flightDao = flightDao;
    }
    @Override
    public Packet execute(Object data) {
        String airlineName = (String) data;
        try {
            List<Flight> flights = flightDao.findFlightByAirlineName(airlineName);
            Gson gson = new Gson();
            String jsonFlights = gson.toJson(flights);
//            System.out.println("Find flight by airline name: " + jsonFlights); //Debugging line
            return new Packet(jsonFlights);
        } catch (DaoException e) {
            return new Packet(e);
        }
    }
}
