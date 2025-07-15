package top.houyuji.common.satoken.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.houyuji.common.base.core.UserContext;
import top.houyuji.common.base.core.UserInfo;

@Slf4j
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
            SaRouter.match("/**").notMatch("/auth/login").notMatch("/auth/logout").check(r -> {
                StpUtil.checkLogin();
                UserInfo userinfo = (UserInfo) StpUtil.getSession().get("userinfo");
                UserContext.set(userinfo);
            });
            SaRouter.match("/sys/**", r -> StpUtil.checkRole("admin"));
            // 用户管理
            SaRouter.match(SaHttpMethod.POST).match("/sys/user", r -> StpUtil.checkPermission("sys:user:add"));
            SaRouter.match(SaHttpMethod.DELETE).match("/sys/user", r -> StpUtil.checkPermission("sys:user:delete"));
            SaRouter.match(SaHttpMethod.PUT).match("/sys/user", r -> StpUtil.checkPermission("sys:user:update"));
            SaRouter.match(SaHttpMethod.GET).match("/sys/user", r -> StpUtil.checkPermission("sys:user:query"));
            SaRouter.match(SaHttpMethod.PUT).match("/sys/user/resetPassword", r -> StpUtil.checkPermission("sys:user:update"));
            // 角色管理
            SaRouter.match(SaHttpMethod.POST).match("/sys/role", r -> StpUtil.checkPermission("sys:role:add"));
            SaRouter.match(SaHttpMethod.DELETE).match("/sys/role", r -> StpUtil.checkPermission("sys:role:delete"));
            SaRouter.match(SaHttpMethod.PUT).match("/sys/role", r -> StpUtil.checkPermission("sys:role:update"));
            SaRouter.match(SaHttpMethod.GET).match("/sys/role", r -> StpUtil.checkPermission("sys:role:query"));
            SaRouter.match(SaHttpMethod.PUT).match("/sys/role/assignPermission", r -> StpUtil.checkPermission("sys:role:assignPermission"));
            // 权限管理
            SaRouter.match(SaHttpMethod.POST).match("/sys/permission", r -> StpUtil.checkPermission("sys:permission:add"));
            SaRouter.match(SaHttpMethod.DELETE).match("/sys/permission", r -> StpUtil.checkPermission("sys:permission:delete"));
            SaRouter.match(SaHttpMethod.PUT).match("/sys/permission", r -> StpUtil.checkPermission("sys:permission:update"));
            SaRouter.match(SaHttpMethod.GET).match("/sys/permission", r -> StpUtil.checkPermission("sys:permission:query"));
            SaRouter.match(SaHttpMethod.GET).match("/sys/permission/menu/tree", r -> StpUtil.checkPermission("sys:permission:query"));
            // 系统公告管理
            SaRouter.match(SaHttpMethod.POST).match("/sys/notice", r -> StpUtil.checkPermission("sys:notice:add"));
            SaRouter.match(SaHttpMethod.DELETE).match("/sys/notice", r -> StpUtil.checkPermission("sys:notice:delete"));
            SaRouter.match(SaHttpMethod.PUT).match("/sys/notice", r -> StpUtil.checkPermission("sys:notice:update"));
            SaRouter.match(SaHttpMethod.GET).match("/sys/notice", r -> StpUtil.checkPermission("sys:notice:query"));
        }));
    }
}
