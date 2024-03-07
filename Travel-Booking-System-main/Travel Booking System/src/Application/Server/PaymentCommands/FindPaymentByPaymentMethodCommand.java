package Application.Server.PaymentCommands;

import Application.DAOs.PaymentDaoInterface;
import Application.DTOs.Payment;
import Application.Exceptions.DaoException;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import java.util.List;

public class FindPaymentByPaymentMethodCommand implements Command{
    private PaymentDaoInterface paymentDao;
    public FindPaymentByPaymentMethodCommand(PaymentDaoInterface paymentDao){
        this.paymentDao = paymentDao;
    }
    public Packet execute(Object data){
        String paymentMethod = (String) data;
        try{
            List<Payment> payments = paymentDao.findPaymentByPaymentMethod(paymentMethod);
            Gson gson = new Gson();
            String jsonPayments = gson.toJson(payments);
            return new Packet(jsonPayments);
        }catch (DaoException e){
            return new Packet(e);
        }
    }
}