package cn.codeartist.xdiamond.common.enums;

/**
 * @author 艾江南
 * @date 2019/4/13
 */
public enum Command {

    /**
     * 成功、失败、心跳检测、获取配置、配置已修改
     */
    OK(-0b001), ERROR(0b001), HEAR_BEAT(0b010), GET_CONFIG(0b011), CONFIG_CHANGED(0b100);

    private int code;

    Command(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
