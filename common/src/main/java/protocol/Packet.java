package protocol;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public abstract class Packet {

    public void setVersion(Byte version) {
        this.version = version;
    }

    @JSONField(serialize = false, deserialize = false)
    private Byte version = 1;

    @JSONField(serialize = false)
    public abstract Byte getCommand();

    public Byte getVersion() {
        return version;
    }
}
