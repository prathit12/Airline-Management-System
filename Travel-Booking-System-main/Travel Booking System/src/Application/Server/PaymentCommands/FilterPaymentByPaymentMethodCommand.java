package Application.Server.PaymentCommands;

import Application.DAOs.PaymentDaoInterface;
import Application.DTOs.Payment;
import Application.Exceptions.DaoException;
import Application.Server.Command;
import Application.Protocol.Packet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
public class FilterPaymentByPaymentMethodCommand implements Command{
    private PaymentDaoInterface paymentDao;
    public FilterPaymentByPaymentMethodCommand(PaymentDaoInterface paymentDao){
        this.paymentDao = paymentDao;
    }
    public Packet execute(Object data){
        try{
            Set<String> uniquePaymentMethods = paymentDao.uniquePaymentMethod();
            HashMap<Integer, String> numberedPaymentMethods = new HashMap<>();
            int i = 1;
            for (String paymentMethod : uniquePaymentMethods) {
                numberedPaymentMethods.put(i, paymentMethod);
                i++;
            }
            Gson gson = new Gson();
            String jsonNumberedPaymentMethods = gson.toJson(numberedPaymentMethods);
            return new Packet(jsonNumberedPaymentMethods);
        }catch (DaoException e){
            return new Packet(e);
        }
    }
}