package Application.Server.FlightCommands;

import Application.DAOs.FlightDaoInterface;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import java.util.TreeSet;
public class GetFlightNumbersCacheCommand implements Command {
    private FlightDaoInterface flightDao;
    public GetFlightNumbersCacheCommand(FlightDaoInterface flightDao){
        this.flightDao = flightDao;
    }
    @Override
    public Packet execute(Object data){
        try{
            TreeSet<String> flightNumbers = flightDao.populateFlightCache();
            Gson gson = new Gson();
            return new Packet(gson.toJson(flightNumbers));
        }catch(Exception e){
            return new Packet(e);
        }
    }
}


