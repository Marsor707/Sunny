package protocol.command;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public interface Command {

    Byte HEART_BEAT_REQUEST = 1;
    Byte HEART_BEAT_RESPONSE = 2;
    Byte LOGIN_REQUEST = 3;
    Byte LOGIN_RESPONSE = 4;
    Byte MESSAGE_REQUEST = 5;
    Byte MESSAGE_RESPONSE = 6;
    Byte SYSTEM_MESSAGE_REQUEST = 7;
    Byte SYSTEM_MESSAGE_RESPONSE = 8;
    Byte CHECK_ONLINE_STATE_REQUEST = 9;
    Byte CHECK_ONLINE_STATE_RESPONSE = 10;
}
