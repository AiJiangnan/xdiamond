package cn.codeartist.xdiamond.server.handler;

import cn.codeartist.xdiamond.server.entity.RestResponse;
import cn.codeartist.xdiamond.server.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 *
 * @author 艾江南
 * @date 2018/9/7
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 参数校验异常转换成400
     * ConstraintViolationException：当验证注解使用在Controller的方法参数上时，在Controller类上使用@Validated注解生效
     * BindException：当验证注解使用在实体类字段上时，表单提交使用实体类接收参数，在参数前使用@Valid
     * MethodArgumentNotValidException：当验证注解使用在实体类字段上时，RequestBody提交使用实体类接收参数，在参数前使用@Valid
     *
     * @param e 异常
     * @return 参数异常响应
     */
    @ExceptionHandler({ConstraintViolationException.class, BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public RestResponse validationExceptionHandler(Exception e) {
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violationSet = exception.getConstraintViolations();
            ConstraintViolation<?> violation = violationSet.iterator().next();
            return RestResponse.badRequest(violation.getMessage());
        }
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            return RestResponse.badRequest(errors.get(0).getDefaultMessage());
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            return RestResponse.badRequest(errors.get(0).getDefaultMessage());
        }
        logger.error("Parameter validate exception handler error. ", e);
        return RestResponse.error(e.getMessage());
    }

    /**
     * 业务异常全局处理
     *
     * @param e 异常
     * @return 参数异常响应
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RestResponse businessExceptionHandler(Exception e) {
        e.printStackTrace();
        return RestResponse.fail(e.getMessage());
    }

    /**
     * HTTP请求方法异常处理
     *
     * @param e 异常
     * @return 参数异常响应
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public RestResponse httpRequestMethodNotSupportedExceptionHandler(Exception e, HttpServletRequest request) {
        logger.warn("url:{}, param:{}", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()));
        return RestResponse.badRequest(e.getMessage());
    }

    /**
     * 系统异常全局处理
     *
     * @param e       异常
     * @param request 请求
     * @return 参数异常响应
     */
    @ExceptionHandler
    @ResponseBody
    public RestResponse exceptionHandler(Exception e, HttpServletRequest request) {
        logger.error("{} {}, param:{}", request.getMethod(), request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), e);
        return new RestResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务异常，请联系技术支持", "message: " + e.getMessage() + "  cause by: " + e.getStackTrace()[0]);
    }

}