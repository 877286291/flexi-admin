package top.houyuji.utils;

import cn.dev33.satoken.stp.StpUtil;
import top.houyuji.common.base.AppUtil;
import top.houyuji.common.base.core.UserInfo;
import top.houyuji.sys.domain.dto.UserInfoDTO;
import top.houyuji.sys.service.SysAuthService;

public class SaTokenUtil {
    /**
     * 获取当前用户
     *
     * @return .
     */
    public static UserInfoDTO getCurrentUser() {
        SysAuthService sysAuthService = AppUtil.getBean(SysAuthService.class);
        UserInfo userinfo = (UserInfo) StpUtil.getSession().get("userinfo");
        String username = userinfo.getUsername();
        return sysAuthService.findByUsername(username);
    }

    /**
     * 获取当前用户id
     *
     * @return .
     */
    public static String getUserId() {
        UserInfoDTO currentUser = getCurrentUser();
        if (currentUser != null) {
            return currentUser.getId();
        }
        return null;
    }

    /**
     * 获取当前username
     *
     * @return .
     */
    public static String getUsername() {
        UserInfoDTO currentUser = getCurrentUser();
        if (null != currentUser) {
            return currentUser.getUsername();
        }
        return null;
    }
}
