package Application.Server.AirportCommands;

import Application.DAOs.AirportDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.MenuOptions;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import Application.Server.Command;
import Application.DAOs.FlightDaoInterface;
import Application.DTOs.Flight;
import java.util.List;
import Application.Protocol.MenuOptions.ErrorOption;
public class DeleteAirportByNumberCommand implements Command {
    private AirportDaoInterface airportDao;
    private FlightDaoInterface flightDao;

    public DeleteAirportByNumberCommand(AirportDaoInterface airportDao, FlightDaoInterface flightDao) {
        this.airportDao = airportDao;
        this.flightDao = flightDao;
    }

    public Packet execute(Object data) {
        String airportNumber = (String) data;
        try {
            boolean deleted = airportDao.deleteAirportByNumber(airportNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(deleted));
        } catch (DaoException e) {
            String exceptionMessage = e.getMessage() == null ? "Unknown error" : e.getMessage();
            // System.out.println("DaoException: " + exceptionMessage); // Debugging line
            if (exceptionMessage.contains("foreign key constraint")) {
                try {
                    List<Flight> flights = flightDao.findAllFlightsByAirportNumber(airportNumber);
                    Gson gson = new Gson();
                    String jsonFlights = gson.toJson(flights);
                    Packet packetToSend = new Packet(ErrorOption.DAO_EXCEPTION, e, jsonFlights);
                    // System.out.println("Server: Sending packet: " + packetToSend.toJson()); // Debugging line
                    return packetToSend;
                } catch (DaoException e1) {
                    return new Packet(e1);
                }
            } else {
                return new Packet(e);
            }
        }
    }
}