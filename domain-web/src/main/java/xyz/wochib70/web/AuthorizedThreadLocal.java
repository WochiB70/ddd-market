package xyz.wochib70.web;

import xyz.wochib70.domain.UserId;

import java.util.Optional;

public class AuthorizedThreadLocal {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void set(Long userId) {
        threadLocal.set(userId);
    }

    public static UserId getAdminId() {
        if (getOrThrow() > 0L && getOrThrow() < 1000000L) {
            return new UserId(getOrThrow());
        }
        throw new RuntimeException("用户权限不足");
    }

    public static UserId getUserId() {
        return new UserId(getOrThrow());
    }

    public static Long getOrThrow() {
        return Optional.ofNullable(threadLocal.get())
                .orElseThrow(() -> new RuntimeException("未授权访问"));
    }


    public static void clean() {
        threadLocal.remove();
    }
}
