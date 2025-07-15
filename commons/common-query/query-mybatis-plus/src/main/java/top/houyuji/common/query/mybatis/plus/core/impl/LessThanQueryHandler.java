package top.houyuji.common.query.mybatis.plus.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Nonnull;
import top.houyuji.common.query.annotation.LessThan;
import top.houyuji.common.query.mybatis.plus.core.AbstractQueryHandler;

import java.lang.annotation.Annotation;

public class LessThanQueryHandler extends AbstractQueryHandler {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return LessThan.class;
    }

    @Override
    public void buildQuery(QueryWrapper<?> queryWrapper, String column, @Nonnull Object value) {
        queryWrapper.lt(column, value);
    }
}
