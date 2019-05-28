package protocol;

import io.netty.buffer.ByteBuf;
import protocol.command.Command;
import protocol.request.*;
import protocol.response.CheckOnlineStateResponsePacket;
import protocol.response.LoginResponsePacket;
import protocol.response.MessageResponsePacket;
import protocol.response.SystemMessageResponsePacket;
import serialize.Serializer;
import serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class PacketCodeC {

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    public static final int MAGIC_NUMBER = 0x12345678;
    public static PacketCodeC INSTANCE = new PacketCodeC();

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.HEART_BEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.HEART_BEAT_RESPONSE, HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.SYSTEM_MESSAGE_REQUEST, SystemMessageRequestPacket.class);
        packetTypeMap.put(Command.SYSTEM_MESSAGE_RESPONSE, SystemMessageResponsePacket.class);
        packetTypeMap.put(Command.CHECK_ONLINE_STATE_REQUEST, CheckOnlineStateRequestPacket.class);
        packetTypeMap.put(Command.CHECK_ONLINE_STATE_RESPONSE, CheckOnlineStateResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer jsonSerializer = new JSONSerializer();
        serializerMap.put(jsonSerializer.getSerializeAlgorithm(), jsonSerializer);
    }

    /**
     * 编码
     */
    public void encode(ByteBuf byteBuf, Packet packet) {
        Serializer serializer = Serializer.DEFAULT;
        //1.将对象序列化
        byte[] bytes = serializer.serialize(packet);
        //2.编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(serializer.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    /**
     * 解码
     */
    public Packet decode(ByteBuf byteBuf) {
        //跳过magic number
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();
        byte[] data = new byte[length];
        //数据包
        byteBuf.readBytes(data);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deSerialize(requestType, data);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    public Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
