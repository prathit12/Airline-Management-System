package Application.Server.PaymentCommands;

import Application.DAOs.PaymentDaoInterface;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import java.util.TreeSet;

public class GetPaymentNumbersCacheCommand implements Command{
    private PaymentDaoInterface paymentDao;
    public GetPaymentNumbersCacheCommand(PaymentDaoInterface paymentDao){
        this.paymentDao = paymentDao;
    }
    @Override
    public Packet execute(Object data){
        try{
            TreeSet<String> flightNumbers = paymentDao.populatePaymentCache();
            Gson gson = new Gson();
            return new Packet(gson.toJson(flightNumbers));
        }catch(Exception e){
            return new Packet(e);
        }
    }
}
