package top.houyuji.sys.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.houyuji.common.base.exception.UsernameNotFoundException;
import top.houyuji.common.base.utils.StrUtil;
import top.houyuji.sys.domain.dto.UserInfoDTO;
import top.houyuji.sys.domain.entity.SysMenu;
import top.houyuji.sys.domain.entity.SysRole;
import top.houyuji.sys.domain.entity.SysUser;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SysAuthService {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;

    public UserInfoDTO findByUsername(String username) {
        return adminLogin(username);
    }

    /**
     * 登录
     *
     * @param username .
     * @return .
     */
    private UserInfoDTO adminLogin(String username) {
        SysUser user = sysUserService.getByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new UsernameNotFoundException("用户已被禁用");
        }
        // 角色
        List<SysRole> roles = sysRoleService.listByUserId(user.getId());
        // 菜单
        List<SysMenu> menus = sysMenuService.listByUserId(user.getId());

        UserInfoDTO userInfo = new UserInfoDTO();

        // id
        userInfo.setId(user.getId());
        // 用户名
        userInfo.setUsername(user.getUsername());
        // 密码
        userInfo.setPassword(user.getPassword());
        // 昵称
        userInfo.setNickname(user.getNickname());
        // 性别
//        userInfo.setGender(user.getGender());
        // 邮箱
        userInfo.setEmail(user.getEmail());
        // 电话
        userInfo.setPhone(user.getPhone());
        // 头像
        userInfo.setAvatar(user.getAvatar());
        // 角色
        userInfo.setRoles(getRolesByAdmin(roles));
        // 权限
        userInfo.setPermissions(getPermissionsByAdmin(menus));
        // 是否启用
        userInfo.setEnabled(user.getEnabled());
        return userInfo;
    }

    private List<String> getPermissionsByAdmin(List<SysMenu> menus) {
        if (menus == null || menus.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return menus.stream()
                .filter(e -> Boolean.TRUE.equals(e.getEnabled()))
                .map(SysMenu::getPermission)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
    }

    private List<String> getRolesByAdmin(List<SysRole> roles) {
        if (roles == null || roles.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return roles.stream()
                .filter(e -> Boolean.TRUE.equals(e.getEnabled()))
                .map(SysRole::getCode)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
    }
}