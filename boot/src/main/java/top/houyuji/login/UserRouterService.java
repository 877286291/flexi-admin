package top.houyuji.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.houyuji.common.satoken.domain.vo.RouteVO;
import top.houyuji.sys.domain.dto.PermissionDTO;
import top.houyuji.sys.service.SysPermissionService;
import top.houyuji.util.RouteUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRouterService implements top.houyuji.common.satoken.service.UserRouterService {
    private final SysPermissionService sysPermissionService;

    @Override
    public List<RouteVO> getRoutes(String userId) {
        List<PermissionDTO> sysPermissions = sysPermissionService.userRoutes(userId);
        return getRoutes(sysPermissions);
    }


    /**
     * 获取路由
     *
     * @param permissions 权限
     * @return 路由
     */
    private List<RouteVO> getRoutes(List<PermissionDTO> permissions) {
        if (null == permissions) {
            return List.of();
        }
        return RouteUtil.buildRoutes(permissions);
    }
}
