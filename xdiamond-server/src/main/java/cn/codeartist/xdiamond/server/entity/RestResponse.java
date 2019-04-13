package cn.codeartist.xdiamond.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * REST接口返回响应格式
 *
 * @author 艾江南
 * @date 2018/9/5
 */
@Data
@AllArgsConstructor
public final class RestResponse<T> {

    /**
     * 返回码
     */
    private int status;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    private RestResponse(int code, String msg) {
        this(code, msg, null);
    }

    /**
     * 没有权限
     * 403 forbidden
     *
     * @return REST接口返回响应
     */
    public static RestResponse forbidden() {
        return new RestResponse(403, "Forbidden");
    }

    public static RestResponse forbidden(String msg) {
        return new RestResponse<>(403, msg);
    }

    /**
     * 业务异常
     * 503 Service Unavailable
     *
     * @return REST接口返回响应
     */
    public static RestResponse fail() {
        return new RestResponse(503, "Service Unavailable");
    }

    public static RestResponse fail(String msg) {
        return new RestResponse<>(503, msg);
    }

    /**
     * 服务器错误异常
     * 500 Internal Server Error
     *
     * @return REST接口返回响应
     */
    public static RestResponse error() {
        return new RestResponse(500, "Internal Server Error");
    }

    public static RestResponse error(String msg) {
        return new RestResponse<>(500, msg);
    }

    /**
     * 请求错误、参数校验错误
     * 400 Bad Request
     *
     * @return REST接口返回响应
     */
    public static RestResponse badRequest() {
        return new RestResponse(400, "Bad Request");
    }

    public static RestResponse badRequest(String msg) {
        return new RestResponse<>(400, msg);
    }

    /**
     * 调用成功返回
     * 200 ok
     *
     * @return REST接口返回响应
     */
    public static RestResponse ok() {
        return ok(null);
    }

    public static <T> RestResponse<T> ok(T data) {
        return new RestResponse<>(200, "OK", data);
    }
}