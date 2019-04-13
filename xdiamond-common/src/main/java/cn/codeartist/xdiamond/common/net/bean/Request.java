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
public class Request {

    private short type;
    private int id;
    private int command;
    private Map<String, Object> data;

    public Request() {
        this.type = MessageType.REQUEST.code();
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static class RequestBuilder {
        private Request request;

        public RequestBuilder() {
            request = new Request();
        }

        public RequestBuilder id(int id) {
            request.setId(id);
            return this;
        }

        public RequestBuilder command(Command command) {
            request.setCommand(command.code());
            return this;
        }

        public RequestBuilder data(Map<String, Object> data) {
            request.setData(data);
            return this;
        }

        public RequestBuilder data(String key, Object value) {
            request.putData(key, value);
            return this;
        }

        public Request build() {
            return request;
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
