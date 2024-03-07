package Application.Server.CustomerCommands;

import Application.DAOs.CustomerBookingDaoInterface;
import Application.DTOs.CustomerBooking;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;

import java.util.List;

public class FindRelatedBookingWithFlightForCustomerCommand implements Command {
    private CustomerBookingDaoInterface customerBookingDao;
    public FindRelatedBookingWithFlightForCustomerCommand(CustomerBookingDaoInterface customerBookingDao){
        this.customerBookingDao = customerBookingDao;
    }
    @Override
    public Packet execute(Object data){
        try{
            List<CustomerBooking> customerBookings = customerBookingDao.findAllCustomerBookings();
            Gson gson = new Gson();
            return new Packet(gson.toJson(customerBookings));
        }catch(Exception e){
            return new Packet(e);
        }
    }
}
