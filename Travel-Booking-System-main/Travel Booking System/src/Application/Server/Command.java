package Application.Server;

import Application.Protocol.Packet;

public interface Command {
    Packet execute(Object data);
}
