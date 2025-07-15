package top.houyuji.common.query.mybatis.plus.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import top.houyuji.common.query.annotation.IsNull;
import top.houyuji.common.query.mybatis.plus.core.AbstractQueryHandler;

import java.lang.annotation.Annotation;

@Slf4j
public class IsNullQueryHandler extends AbstractQueryHandler {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return IsNull.class;
    }

    @Override
    public void buildQuery(QueryWrapper<?> queryWrapper, String column, @Nonnull Object value) {
        queryWrapper.isNull(column);
    }
}
