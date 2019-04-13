package cn.codeartist.xdiamond.common.enums;

/**
 * @author 艾江南
 * @date 2019/4/13
 */
public enum MessageType {

    /**
     * 请求，响应，通知
     */
    REQUEST((short) 0b01), RESPONSE((short) 0b10), NOTICE((short) 0b11);

    private short code;

    MessageType(short code) {
        this.code = code;
    }

    public short code() {
        return this.code;
    }
}
