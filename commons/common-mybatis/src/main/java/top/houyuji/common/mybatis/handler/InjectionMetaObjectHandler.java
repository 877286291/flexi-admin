package top.houyuji.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import top.houyuji.common.base.core.UserContext;
import top.houyuji.common.base.core.UserInfo;
import top.houyuji.common.base.exception.ServiceException;
import top.houyuji.common.mybatis.core.domain.BaseEntity;

import java.util.Date;

@Slf4j
@Component
public class InjectionMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (null != metaObject && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
                Date current = null != baseEntity.getCreated() ? baseEntity.getCreated() : new Date();
                UserInfo userInfo = getUserInfo();
                String username = null;
                if (null != userInfo) {
                    username = userInfo.getUsername();
                }
                this.strictInsertFill(metaObject, "created", Date.class, current);
                this.strictInsertFill(metaObject, "createdBy", String.class, username);
                this.strictInsertFill(metaObject, "modified", Date.class, current);
                this.strictInsertFill(metaObject, "modifiedBy", String.class, username);
            }

        } catch (Exception e) {
            throw new ServiceException("mybatis自动填充用户信息异常", 500, e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (null != metaObject && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
                Date current = null != baseEntity.getModified() ? baseEntity.getModified() : new Date();
                UserInfo userInfo = getUserInfo();
                String username = null;
                if (null != userInfo) {
                    username = userInfo.getUsername();
                }
                this.strictUpdateFill(metaObject, "modified", Date.class, current);
                this.strictUpdateFill(metaObject, "modifiedBy", String.class, username);
            }
        } catch (Exception e) {
            throw new ServiceException("mybatis自动填充用户信息异常", 500, e);
        }
    }

    private UserInfo getUserInfo() {
        try {
            return UserContext.get();
        } catch (Exception e) {
            log.warn("mybatis自动填充用户信息警告，获取当前用户信息失败！请检查是否在请求上下文中设置了用户信息。UserContext.set(UserInfo userInfo)");
            return null;
        }

    }
}
