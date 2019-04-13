package cn.codeartist.xdiamond.server.util;

import cn.codeartist.xdiamond.server.exception.BusinessException;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 业务断言
 *
 * @author 艾江南
 * @date 2019/3/21
 */
public final class Assert {

    /**
     * 判断表达式是否为真
     *
     * @param expression 表达式
     * @param message    业务异常消息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            error(message);
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param object  对象
     * @param message 业务异常消息
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            error(message);
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param object    对象
     * @param exception 异常消息
     */
    public static void notNull(Object object, RuntimeException exception) {
        if (object == null) {
            throw exception;
        }
    }

    /**
     * 判断集合是否为空（为空抛出）
     *
     * @param object  集合对象
     * @param message 业务异常消息
     */
    public static void notEmpty(Collection object, String message) {
        if (object == null || object.isEmpty()) {
            error(message);
        }
    }

    /**
     * 判断集合是否为空（不为空抛出）
     *
     * @param object  集合对象
     * @param message 业务异常消息
     */
    public static void isEmpty(Collection object, String message) {
        if (object != null && !object.isEmpty()) {
            error(message);
        }
    }

    /**
     * 判断字符串是否为空字符串
     *
     * @param string  字符串
     * @param message 业务异常消息
     */
    public static void notBlank(String string, String message) {
        notNull(string, message);
        if (string.trim().isEmpty()) {
            error(message);
        }
    }

    /**
     * 判断对象属性是否为空
     *
     * @param object  对象
     * @param message 业务消息异常
     */
    public static void notField(Object object, String message) {
        boolean flag = false;
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                field.setAccessible(false);
                if (value instanceof String && ((String) value).trim().isEmpty()) {
                    flag = true;
                    break;
                }
                if (value != null) {
                    flag = true;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        isTrue(flag, message);
    }

    private static void error(String message) {
        throw new BusinessException(message);
    }

}
