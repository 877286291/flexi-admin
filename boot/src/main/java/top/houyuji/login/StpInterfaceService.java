package top.houyuji.login;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.houyuji.common.base.core.UserInfo;
import top.houyuji.sys.domain.entity.SysMenu;
import top.houyuji.sys.domain.entity.SysRole;
import top.houyuji.sys.service.SysMenuService;
import top.houyuji.sys.service.SysRoleService;

import java.util.List;

@Service
@AllArgsConstructor
public class StpInterfaceService implements StpInterface {
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSession();
        UserInfo userinfo = (UserInfo) session.get("userinfo");
        String userId = userinfo.getId();
        List<SysMenu> menus = sysMenuService.listByUserId(userId);
        return menus.stream().map(SysMenu::getPermission).toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSession();
        UserInfo userinfo = (UserInfo) session.get("userinfo");
        String userId = userinfo.getId();
        List<SysRole> roles = sysRoleService.listByUserId(userId);
        return roles.stream().map(SysRole::getCode).toList();
    }
}
