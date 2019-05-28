package protocol.request;

import protocol.Packet;
import protocol.command.Command;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class SystemMessageRequestPacket extends Packet {

    private String message;


    public SystemMessageRequestPacket() {
    }

    public SystemMessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.SYSTEM_MESSAGE_REQUEST;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
