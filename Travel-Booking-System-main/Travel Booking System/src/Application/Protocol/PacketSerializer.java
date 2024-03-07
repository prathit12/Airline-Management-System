package Application.Protocol;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PacketSerializer implements JsonSerializer<Packet> {
    @Override
    public JsonElement serialize(Packet src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jObject = new JsonObject();

        if (src.getOption() != null) {
            jObject.addProperty("option", src.getOption().name());
        }

//        if (src.getData() != null) {
//            jObject.add("data", context.serialize(src.getData()));
//        }
        if (src.getData() != null) {
            jObject.add("data", context.serialize(src.getData()));
            jObject.addProperty("dataType", src.getData().getClass().getName());
        }


        if (src.getException() != null) {
            jObject.addProperty("exception", src.getException().getClass().getName());
            jObject.addProperty("exceptionMessage", src.getException().getMessage());
        }

        return jObject;
    }
}
