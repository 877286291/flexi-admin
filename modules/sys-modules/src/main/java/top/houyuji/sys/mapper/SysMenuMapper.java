package top.houyuji.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.houyuji.sys.domain.entity.SysMenu;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色id查询菜单
     *
     * @param roleIds 角色id
     * @return 菜单
     */
    List<SysMenu> listByRoleIds(List<String> roleIds);


    /**
     * 是否有子节点
     *
     * @param id id
     * @return true/false
     */
    @Select("select count(1) from sys_menu where parent_id = #{id}")
    boolean hasChild(String id);

    /**
     * 是否有菜单权限
     *
     * @param menuId 菜单id
     * @return true/false
     */
    @Select("select count(1) from sys_role_menu where menu_id = #{menuId}")
    boolean existsRoleMenu(String menuId);
}
