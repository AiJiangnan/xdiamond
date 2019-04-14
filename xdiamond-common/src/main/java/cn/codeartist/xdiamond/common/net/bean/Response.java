package cn.codeartist.xdiamond.common.net.bean;

import cn.codeartist.xdiamond.common.enums.Command;
import cn.codeartist.xdiamond.common.enums.MessageType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应
 *
 * @author 艾江南
 * @date 2019/4/13
 */
@Data
public class Response {

    private boolean success;
    private short type;
    private int id;
    private int command;
    private String error;
    private Map<String, Object> data;

    public Response() {
        this.type = MessageType.RESPONSE.code();
    }

    public static ResponseBuilder success() {
        return new ResponseBuilder().success(true);
    }

    public static ResponseBuilder fail() {
        return new ResponseBuilder().success(false);
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public static class ResponseBuilder {
        private Response response;

        public ResponseBuilder() {
            response = new Response();
        }

        public ResponseBuilder success(boolean success) {
            response.setSuccess(success);
            return this;
        }

        public ResponseBuilder id(int id) {
            response.setId(id);
            return this;
        }

        public ResponseBuilder command(Command command) {
            response.setCommand(command.code());
            return this;
        }

        public ResponseBuilder data(Map<String, Object> data) {
            response.setData(data);
            return this;
        }

        public ResponseBuilder data(String key, Object value) {
            response.putData(key, value);
            return this;
        }

        public ResponseBuilder error(String error) {
            response.setError(error);
            return this;
        }

        public Response build() {
            return response;
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
