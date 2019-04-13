package cn.codeartist.xdiamond.server.interceptor;

import cn.codeartist.xdiamond.server.entity.RestResponse;
import com.alibaba.fastjson.JSON;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 接口权限拦截器
 *
 * @author 艾江南
 * @date 2019/3/30
 */
@Configuration
@ConfigurationProperties("web.authentication")
public class AuthInterceptor extends HandlerInterceptorAdapter {

    /**
     * 是否启用权限拦截
     */
    private Boolean enable = true;
    /**
     * 权限控制请求头的名称
     */
    private String name = "sign";
    /**
     * 权限控制请求头的值
     */
    private String token = "foobar";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (enable && !token.equals(request.getHeader(name))) {
            response.reset();
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().print(JSON.toJSONString(RestResponse.forbidden()));
            return false;
        }
        return true;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
