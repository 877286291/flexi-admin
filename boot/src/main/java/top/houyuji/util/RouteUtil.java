package top.houyuji.util;

import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import top.houyuji.common.base.enums.MenuTypeEnums;
import top.houyuji.common.base.utils.CollectionUtil;
import top.houyuji.common.base.utils.TreeUtil;
import top.houyuji.common.satoken.domain.vo.RouteMetaVO;
import top.houyuji.common.satoken.domain.vo.RouteVO;
import top.houyuji.sys.domain.dto.PermissionDTO;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RouteUtil {
    /**
     * 构建路由
     *
     * @param list .
     * @return .
     */
    public List<RouteVO> buildRoutes(List<PermissionDTO> list) {
        List<PermissionDTO> tree = buildTree(list);
        if (CollectionUtil.isEmpty(tree)) {
            return null;
        }
        return _buildRoutes(tree);
    }


    /**
     * 构建树
     *
     * @param dataList 数据
     * @return 树
     */
    private List<PermissionDTO> buildTree(List<PermissionDTO> dataList) {
        if (CollectionUtil.isEmpty(dataList)) {
            return null;
        }
        dataList.removeIf(PermissionDTO -> MenuTypeEnums.Menu_4.getValue().equals(PermissionDTO.getMenuType()));
        if (CollectionUtil.isEmpty(dataList)) {
            return null;
        }
        return TreeUtil.buildTree(dataList);
    }


    /**
     * 构建路由
     *
     * @param dtoList .
     * @return .x
     */
    private List<RouteVO> _buildRoutes(@Nonnull List<PermissionDTO> dtoList) {
        List<RouteVO> routes = new ArrayList<>();
        dtoList.forEach(
                item -> {
                    RouteVO route = new RouteVO();
                    // 路由路径
                    route.setPath(item.getPath());
                    // 路由名称
                    route.setName(item.getRouteName());
                    // 路由组件
                    route.setComponent(item.getComponent());
                    // 路由重定向
                    route.setRedirect(item.getRedirect());
                    // 路由元信息
                    route.setMeta(buildMeta(item));
                    // 子路由
                    List<PermissionDTO> children = item.getChildren();
                    if (CollectionUtil.isNotEmpty(children)) {
                        route.setChildren(_buildRoutes(children));
                    }
                    routes.add(route);
                }
        );
        return routes;
    }

    /**
     * 构建meta
     *
     * @param dto .
     * @return .
     */
    private RouteMetaVO buildMeta(PermissionDTO dto) {
        RouteMetaVO meta = new RouteMetaVO();
        // 菜单标题
        meta.setTitle(dto.getTitle());
        // 菜单图标
        meta.setIcon(dto.getIcon());
        // 顶级路由
        if (isRoot(dto)) {
            // 菜单排序
            meta.setRank(dto.getRank());
        } else {
            // 是否显示父菜单
            meta.setShowParent(dto.getShowParent());
        }
        // 是否展示
        meta.setShowLink(dto.getShowLink());
        // 是否缓存
        meta.setKeepAlive(dto.getKeepAlive());
        // 需要内嵌的iframe链接地址
        meta.setFrameSrc(dto.getFrameSrc());
        return meta;
    }

    /**
     * 是否是根节点
     *
     * @param dto .
     * @return .
     */
    private boolean isRoot(PermissionDTO dto) {
        return null == dto.getParentId();
    }
}