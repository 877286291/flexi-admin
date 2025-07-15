package top.houyuji.common.base.utils;

import jakarta.annotation.Nullable;

public class CollectionUtil extends cn.hutool.core.collection.CollectionUtil {

    /**
     * 将集合转换为另一种类型的集合
     *
     * @param list   集合
     * @param mapper 转换函数
     * @param <T>    原类型
     * @param <R>    目标类型
     * @return 目标类型的集合
     */
    @Nullable
    public static <T, R> java.util.List<R> listToList(java.util.List<T> list, java.util.function.Function<? super T, ? extends R> mapper) {
        if (isEmpty(list)) {
            return null;
        }
        return list.stream().map(mapper).collect(java.util.stream.Collectors.toList());
    }
}
