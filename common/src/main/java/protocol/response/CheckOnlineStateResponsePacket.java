package protocol.response;

import protocol.Packet;
import protocol.command.Command;

import java.util.Map;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class CheckOnlineStateResponsePacket extends Packet {

    private Map<String, Boolean> onlineStateMap;

    @Override
    public Byte getCommand() {
        return Command.CHECK_ONLINE_STATE_RESPONSE;
    }

    public Map<String, Boolean> getOnlineStateMap() {
        return onlineStateMap;
    }

    public void setOnlineStateMap(Map<String, Boolean> onlineStateMap) {
        this.onlineStateMap = onlineStateMap;
    }
}
