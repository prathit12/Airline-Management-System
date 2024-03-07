package Application.Protocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Packet {
    private Enum option;
    private Object data;
    private Throwable exception;
    private String exceptionMessage;

    public Packet(Enum option, Object data) {
        this.option = option;
        this.data = data;
    }

    public Packet(MenuOptions.ErrorOption errorOption, Throwable exception, String exceptionMessage, Object data) {
        this.option = errorOption;
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
        this.data = data;
    }

//    public Packet(Enum option, Throwable exception, Object data) {
//        this.option = option;
//        this.exception = exception;
//        this.exceptionMessage = exception.getClass().getSimpleName() + ": " + exception.getMessage();
//        this.data = data;
//    }

    public Packet(Enum option, Throwable exception, Object data) {
        this.option = option;
        this.exception = exception;
        this.exceptionMessage = exception.getClass().getSimpleName() + ": " + exception.getMessage();
        this.data = data;
    }


    public Packet(Throwable exception, String exceptionMessage, Object data) {
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
        this.data = data;
    }

    public Packet(Throwable exception) {
        this.exception = exception;
    }

    public Packet(Object data) {
        this.data = data;
    }

    public Enum getOption() {
        return option;
    }

    public Object getData() {
        return data;
    }

    public Throwable getException() {
        return exception;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Packet.class, new PacketSerializer())
                .create();
        return gson.toJson(this);
    }

    public static Packet fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Packet.class, new PacketDeserializer())
                .create();
        return gson.fromJson(json, Packet.class);
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
