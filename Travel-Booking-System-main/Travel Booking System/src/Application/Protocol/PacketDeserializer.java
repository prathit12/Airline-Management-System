package Application.Protocol;

import com.google.gson.*;
import java.lang.reflect.Type;

public class PacketDeserializer implements JsonDeserializer<Packet> {
    @Override
    public Packet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Enum option = null;
        if (jsonObject.has("option")) {
            String optionName = jsonObject.get("option").getAsString();
            option = findOptionInMenuOptions(optionName);
        }

        Object data = null;
        if (jsonObject.has("data") && jsonObject.has("dataType")) {
            try {
                Class<?> dataClass = Class.forName(jsonObject.get("dataType").getAsString());
                data = context.deserialize(jsonObject.get("data"), dataClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

//        if (jsonObject.has("data")) {
//            data = context.deserialize(jsonObject.get("data"), Object.class);
//        }

        Throwable exception = null;
        String exceptionMessage = null;
        if (jsonObject.has("exception") && jsonObject.has("exceptionMessage")) {
            try {
                exception = (Throwable) Class.forName(jsonObject.get("exception").getAsString()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            exceptionMessage = jsonObject.get("exceptionMessage").getAsString();
        }

        if (option != null && exception != null) {
            return new Packet(option, exception, data);
        } else if (option != null) {
            return new Packet(option, data);
        } else if (exception != null) {
            return new Packet(exception, exceptionMessage, data);
        } else {
            return new Packet(data);
        }
    }

    private Enum findOptionInMenuOptions(String optionName) {
        try {
            return MenuOptions.CustomerMenuOptions.valueOf(optionName);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Ignored
        }
        try {
            return MenuOptions.AirportMenuOptions.valueOf(optionName);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Ignored
        }
        try {
            return MenuOptions.FlightMenuOptions.valueOf(optionName);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Ignored
        }
        try {
            return MenuOptions.BookingMenuOptions.valueOf(optionName);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Ignored
        }
        try {
            return MenuOptions.PaymentMenuOptions.valueOf(optionName);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Ignored
        }
        try {
            return MenuOptions.VerifyMenuOptions.valueOf(optionName);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Ignored
        }
        try {
            return MenuOptions.ErrorOption.valueOf(optionName);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Ignored
        }
        return null;
    }
}
