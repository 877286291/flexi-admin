package top.houyuji.common.mybatis.core.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.houyuji.common.mybatis.core.domain.BaseEntity;

public class BaseService<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements IService<T> {
}
