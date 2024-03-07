package Application.Server.PaymentCommands;

import Application.DAOs.PaymentDaoInterface;
import Application.Exceptions.DaoException;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import Application.Server.Command;
public class DeletePaymentByNumberCommand implements Command{
    private PaymentDaoInterface paymentDao;
    public DeletePaymentByNumberCommand(PaymentDaoInterface paymentDao){
        this.paymentDao = paymentDao;
    }
    public Packet execute(Object data){
        String paymentNumber = (String) data;
        try{
            boolean deleted = paymentDao.deletePaymentByNumber(paymentNumber);
            Gson gson = new Gson();
            return new Packet(gson.toJson(deleted));
        }catch (DaoException e){
            return new Packet(e);
        }
    }
}