package cn.codeartist.xdiamond.common.net.bean;

import cn.codeartist.xdiamond.common.enums.Command;
import cn.codeartist.xdiamond.common.enums.MessageType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 艾江南
 * @date 2019/4/13
 */
@Data
public class Notice {

    private short type;
    private int id;
    private int command;
    private Map<String, Object> data;

    public Notice() {
        this.type = MessageType.NOTICE.code();
    }

    public static NoticeBuilder builder() {
        return new NoticeBuilder();
    }

    public static class NoticeBuilder {
        private Notice notice;

        public NoticeBuilder() {
            notice = new Notice();
        }

        public NoticeBuilder id(int id) {
            notice.setId(id);
            return this;
        }

        public NoticeBuilder command(Command command) {
            notice.setCommand(command.code());
            return this;
        }

        public NoticeBuilder data(Map<String, Object> data) {
            notice.setData(data);
            return this;
        }

        public NoticeBuilder data(String key, Object value) {
            notice.putData(key, value);
            return this;
        }

        public Notice build() {
            return notice;
        }
    }

    private void putData(String key, Object value) {
        if (data == null) {
            data = new HashMap<>(16);
        }
        data.put(key, value);
    }

    public Object deleteData(String key) {
        if (data != null) {
            return data.remove(key);
        }
        return null;
    }

    public Object dataValue(String key) {
        if (data == null) {
            return null;
        }
        return data.get(key);
    }
}
