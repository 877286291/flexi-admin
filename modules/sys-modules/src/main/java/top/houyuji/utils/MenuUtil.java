package top.houyuji.utils;

import jakarta.annotation.Nonnull;
import top.houyuji.common.base.enums.MenuTypeEnums;
import top.houyuji.common.base.utils.CollectionUtil;
import top.houyuji.common.base.utils.TreeUtil;
import top.houyuji.sys.domain.dto.PermissionDTO;
import top.houyuji.sys.domain.vo.MenuTreeVO;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    /**
     * 构建菜单树
     *
     * @param dtoList .
     * @return .
     */
    public static List<MenuTreeVO> buildMenuTree(List<PermissionDTO> dtoList, boolean filterButton) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return null;
        }
        List<PermissionDTO> tree = buildTree(dtoList, filterButton);
        if (CollectionUtil.isEmpty(tree)) {
            return null;
        }
        return _buildMenuTree(tree);
    }

    /**
     * 构建树
     *
     * @param dataList     数据
     * @param filterButton 是否过滤按钮
     * @return 树
     */
    private static List<PermissionDTO> buildTree(List<PermissionDTO> dataList, boolean filterButton) {
        if (CollectionUtil.isEmpty(dataList)) {
            return null;
        }
        if (filterButton) {
            dataList.removeIf(PermissionDTO -> MenuTypeEnums.Menu_4.getValue().equals(PermissionDTO.getMenuType()));
        }
        if (CollectionUtil.isEmpty(dataList)) {
            return null;
        }
        return TreeUtil.buildTree(dataList);
    }

    private static List<MenuTreeVO> _buildMenuTree(@Nonnull List<PermissionDTO> dtoList) {
        List<MenuTreeVO> menuTree = new ArrayList<>();
        dtoList.forEach(
                item -> {
                    MenuTreeVO tree = new MenuTreeVO();
                    tree.setId(item.getId());
                    tree.setParentId(item.getParentId());
                    tree.setTitle(item.getTitle());
                    tree.setId(item.getId());
                    //子类
                    List<PermissionDTO> children = item.getChildren();
                    if (CollectionUtil.isNotEmpty(children)) {
                        tree.setChildren(_buildMenuTree(children));
                    }
                    menuTree.add(tree);
                }
        );
        return menuTree;
    }

}
