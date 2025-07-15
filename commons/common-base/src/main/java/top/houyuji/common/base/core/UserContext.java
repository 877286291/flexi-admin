package top.houyuji.common.base.core;


public class UserContext {

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(UserInfo userInfo) {
        THREAD_LOCAL.set(userInfo);
    }

    public static UserInfo get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
