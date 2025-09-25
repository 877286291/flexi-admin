package top.houyuji.sys.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.houyuji.common.base.core.UserInfo;
import top.houyuji.sys.domain.entity.SysMenu;
import top.houyuji.sys.domain.entity.SysRole;

import java.util.List;
import java.util.Collections;

@Service
@AllArgsConstructor
public class StpInterfaceService implements StpInterface {
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String userId = loginId != null ? String.valueOf(loginId) : null;
        if (userId == null || userId.isEmpty()) {
            SaSession session = StpUtil.getSession(false);
            if (session == null) {
                return Collections.emptyList();
            }
            UserInfo userinfo = (UserInfo) session.get("userinfo");
            if (userinfo == null || userinfo.getId() == null) {
                return Collections.emptyList();
            }
            userId = userinfo.getId();
        }
        List<SysMenu> menus = sysMenuService.listByUserId(userId);
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        return menus.stream().map(SysMenu::getPermission).toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String userId = loginId != null ? String.valueOf(loginId) : null;
        if (userId == null || userId.isEmpty()) {
            SaSession session = StpUtil.getSession(false);
            if (session == null) {
                return Collections.emptyList();
            }
            UserInfo userinfo = (UserInfo) session.get("userinfo");
            if (userinfo == null || userinfo.getId() == null) {
                return Collections.emptyList();
            }
            userId = userinfo.getId();
        }
        List<SysRole> roles = sysRoleService.listByUserId(userId);
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream().map(SysRole::getCode).toList();
    }
}
