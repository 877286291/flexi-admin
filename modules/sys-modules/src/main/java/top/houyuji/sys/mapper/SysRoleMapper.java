package top.houyuji.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.houyuji.sys.domain.entity.SysRole;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 编码是否存在
     *
     * @param code 编码
     * @param id   需要排除的id
     * @return 是否存在
     */
    @Select("SELECT COUNT(1) FROM sys_role WHERE code = #{code} AND id != #{id}")
    boolean existsCodeByNotId(String code, String id);

    /**
     * 编码是否存在
     *
     * @param code 编码
     * @return 是否存在
     */
    @Select("SELECT COUNT(1) FROM sys_role WHERE code = #{code}")
    boolean existsCode(String code);

    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return 角色
     */
    @Select("SELECT r.* FROM sys_role r " +
            "LEFT JOIN sys_user_role ur ON ur.role_id = r.id " +
            "WHERE ur.user_id = #{userId}")
    List<SysRole> listByUserId(String userId);


    /**
     * 是否已绑定用户
     *
     * @param roleId 角色id
     * @return 是否已绑定用户
     */
    @Select("SELECT COUNT(1) FROM sys_user_role WHERE role_id = #{roleId}")
    boolean existsUserByRoleId(String roleId);

    /**
     * 根据角色id查询权限id
     *
     * @param roleId 角色id
     * @return 权限id
     */
    @Select("SELECT permission_id FROM sys_role_permission WHERE role_id = #{roleId}")
    List<String> getPermissionIdsByRoleId(String roleId);

    /**
     * 删除角色菜单
     *
     * @param roleId .
     */
    @Select("DELETE FROM `sys_role_permission` WHERE role_id = #{roleId}")
    void deleteRoleMenu(String roleId);

    /**
     * 插入角色菜单
     *
     * @param roleId .
     * @param menuId .
     */
    void insertRoleMenu(@Param("roleId") String roleId, @Param("permissionIds") List<String> menuId);
}
