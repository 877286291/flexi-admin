package top.houyuji.common.query.mybatis.plus.core.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.houyuji.common.query.annotation.Equals;
import top.houyuji.common.query.mybatis.plus.core.AbstractQueryHandler;

import java.lang.annotation.Annotation;

public class EqualsQueryHandler extends AbstractQueryHandler {

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return Equals.class;
    }

    @Override
    public void buildQuery(QueryWrapper<?> queryWrapper, String column, Object value, Annotation annotation) {
        if (null != annotation) {
            Equals equals = (Equals) annotation;
            if (null == value && equals.allowNull()) {
                queryWrapper.isNull(column);
                return;
            } else if (null == value) {
                return;
            } else if (value instanceof String && ((String) value).isEmpty()) {
                return;
            }
        }
        queryWrapper.eq(column, value);
    }
}
