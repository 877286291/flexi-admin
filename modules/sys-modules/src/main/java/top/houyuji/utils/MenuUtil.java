package top.houyuji.utils;

import jakarta.annotation.Nonnull;
import top.houyuji.common.base.enums.MenuTypeEnums;
import top.houyuji.common.base.utils.CollectionUtil;
import top.houyuji.common.base.utils.TreeUtil;
import top.houyuji.sys.domain.dto.MenuDTO;
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
    public static List<MenuTreeVO> buildMenuTree(List<MenuDTO> dtoList, boolean filterButton) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return null;
        }
        List<MenuDTO> tree = buildTree(dtoList, filterButton);
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
    private static List<MenuDTO> buildTree(List<MenuDTO> dataList, boolean filterButton) {
        if (CollectionUtil.isEmpty(dataList)) {
            return null;
        }
        if (filterButton) {
            dataList.removeIf(MenuDTO -> MenuTypeEnums.Menu_4.getValue().equals(MenuDTO.getMenuType()));
        }
        if (CollectionUtil.isEmpty(dataList)) {
            return null;
        }
        return TreeUtil.buildTree(dataList);
    }

    private static List<MenuTreeVO> _buildMenuTree(@Nonnull List<MenuDTO> dtoList) {
        List<MenuTreeVO> menuTree = new ArrayList<>();
        dtoList.forEach(
                item -> {
                    MenuTreeVO tree = new MenuTreeVO();
                    tree.setId(item.getId());
                    tree.setParentId(item.getParentId());
                    tree.setTitle(item.getTitle());
                    tree.setId(item.getId());
                    //子类
                    List<MenuDTO> children = item.getChildren();
                    if (CollectionUtil.isNotEmpty(children)) {
                        tree.setChildren(_buildMenuTree(children));
                    }
                    menuTree.add(tree);
                }
        );
        return menuTree;
    }

}
