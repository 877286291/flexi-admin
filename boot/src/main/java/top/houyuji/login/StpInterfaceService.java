package top.houyuji.login;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.houyuji.common.base.core.UserInfo;
import top.houyuji.sys.domain.entity.SysPermission;
import top.houyuji.sys.domain.entity.SysRole;
import top.houyuji.sys.service.SysPermissionService;
import top.houyuji.sys.service.SysRoleService;

import java.util.List;

@Service
@AllArgsConstructor
public class StpInterfaceService implements StpInterface {
    private final SysPermissionService sysPermissionService;
    private final SysRoleService sysRoleService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSession();
        UserInfo userinfo = (UserInfo) session.get("userinfo");
        String userId = userinfo.getId();
        List<SysPermission> permissions = sysPermissionService.listByUserId(userId);
        return permissions.stream().map(SysPermission::getPermission).toList();
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
