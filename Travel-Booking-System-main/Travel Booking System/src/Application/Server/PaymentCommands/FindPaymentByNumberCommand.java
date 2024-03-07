package Application.Server.PaymentCommands;

import Application.DAOs.PaymentDaoInterface;
import Application.DTOs.Payment;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class FindPaymentByNumberCommand implements Command{
    private PaymentDaoInterface paymentDao;
    public FindPaymentByNumberCommand(PaymentDaoInterface paymentDao){
        this.paymentDao = paymentDao;
    }
    public Packet execute(Object data){
        String paymentNumber = (String) data;
        try{
            Payment payment = paymentDao.findPaymentByNumber(paymentNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(payment));
        }catch (DaoException e){
            return new Packet(e);
        }
    }
}
