package top.houyuji.common.base.utils;


public class ClassUtil extends cn.hutool.core.util.ClassUtil {

    /**
     * 获取类名
     *
     * @param className className 全类名
     * @return ignore
     */
    public static String getSimpleName(String className) {
        return StrUtil.isBlank(className) ? null : className.substring(className.lastIndexOf(StrUtil.DOT) + 1);
    }
}
