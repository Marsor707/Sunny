package protocol.request;

import protocol.Packet;
import protocol.command.Command;

import java.util.List;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class CheckOnlineStateRequestPacket extends Packet {

    private List<String> pushIds;

    @Override
    public Byte getCommand() {
        return Command.CHECK_ONLINE_STATE_REQUEST;
    }

    public List<String> getPushIds() {
        return pushIds;
    }

    public void setPushIds(List<String> pushIds) {
        this.pushIds = pushIds;
    }
}
