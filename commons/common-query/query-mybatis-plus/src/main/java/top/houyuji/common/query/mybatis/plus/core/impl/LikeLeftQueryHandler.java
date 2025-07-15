package top.houyuji.common.query.mybatis.plus.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Nonnull;
import top.houyuji.common.query.annotation.LikeLeft;
import top.houyuji.common.query.mybatis.plus.core.AbstractQueryHandler;

import java.lang.annotation.Annotation;

public class LikeLeftQueryHandler extends AbstractQueryHandler {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return LikeLeft.class;
    }

    @Override
    public void buildQuery(QueryWrapper<?> queryWrapper, String column, @Nonnull Object value) {
        queryWrapper.likeLeft(column, value);
    }
}
