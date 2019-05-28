package protocol.response;

import protocol.Packet;
import protocol.command.Command;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class MessageResponsePacket extends Packet {

    private String fromPushId;
    private String message;

    public MessageResponsePacket(String fromPushId, String message) {
        this.fromPushId = fromPushId;
        this.message = message;
    }

    public MessageResponsePacket() {
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

    public String getFromPushId() {
        return fromPushId;
    }

    public void setFromPushId(String fromPushId) {
        this.fromPushId = fromPushId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
