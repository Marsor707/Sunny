package protocol.request;

import protocol.Packet;
import protocol.command.Command;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class MessageRequestPacket extends Packet {

    private String toPushId;
    private String message;

    public MessageRequestPacket(String toPushId, String message) {
        this.toPushId = toPushId;
        this.message = message;
    }

    public MessageRequestPacket() {
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

    public String getToPushId() {
        return toPushId;
    }

    public void setToPushId(String toPushId) {
        this.toPushId = toPushId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
