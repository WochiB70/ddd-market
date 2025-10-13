package xyz.wochib70.domain.utils;

public class ParameterUtil {

    private ParameterUtil() {
        throw new UnsupportedOperationException("这是一个工具类不能进行初始化！");
    }

    public static void requireNonNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
    }

    public static void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireNonBlank(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("参数不能为空！");
        }
    }

    public static void requireNonBlank(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言表达式为真
     *
     * @param expression 表达式
     * @throws IllegalArgumentException expression为true
     */
    public static void requireExpression(boolean expression) {
        if (expression) {
            throw new IllegalArgumentException("参数错误！");
        }
    }

    /**
     * 断言表达式为真
     *
     * @param expression 表达式
     * @throws IllegalArgumentException expression为true
     */
    public static void requireExpression(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
