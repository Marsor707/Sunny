package protocol.request;

import protocol.Packet;
import protocol.command.Command;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class LoginRequestPacket extends Packet {

    private String pushId;

    public LoginRequestPacket(String pushId) {
        this.pushId = pushId;
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
