package Application.Server.PaymentCommands;

import Application.DAOs.PaymentDaoInterface;
import Application.DTOs.Payment;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import Application.Server.Command;
import com.google.gson.Gson;
public class InsertPaymentCommand implements Command{
    private PaymentDaoInterface paymentDao;
    public InsertPaymentCommand(PaymentDaoInterface paymentDao){
        this.paymentDao = paymentDao;
    }
    public Packet execute(Object data){
        Payment payment = (Payment) data;
        try{
            Payment p = paymentDao.insertPayment(payment);
            Gson gson = new Gson();
            return new Packet(gson.toJson(p));
        }catch (DaoException e){
            return new Packet(e);
        }
    }
}