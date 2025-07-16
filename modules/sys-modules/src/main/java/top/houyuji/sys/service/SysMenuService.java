package top.houyuji.sys.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.houyuji.common.base.exception.ServiceException;
import top.houyuji.common.base.utils.CollectionUtil;
import top.houyuji.common.mybatis.core.service.BaseService;
import top.houyuji.common.query.mybatis.plus.QueryHelper;
import top.houyuji.sys.domain.dto.MenuDTO;
import top.houyuji.sys.domain.dto.MenuSaveDTO;
import top.houyuji.sys.domain.entity.SysMenu;
import top.houyuji.sys.domain.entity.SysRole;
import top.houyuji.sys.domain.query.MenuQuery;
import top.houyuji.sys.mapper.SysMenuMapper;
import top.houyuji.sys.service.mapstruct.SysMenuMapstruct;
import top.houyuji.sys.service.mapstruct.SysMenuSaveMapstruct;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class SysMenuService extends BaseService<SysMenuMapper, SysMenu> {

    private final SysMenuMapstruct sysMenuMapstruct;
    private final SysMenuSaveMapstruct sysMenuSaveMapstruct;
    @Lazy
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 用户路由
     *
     * @param userId .
     * @return .
     */
    public List<MenuDTO> userRoutes(String userId) {
        List<SysRole> roles = sysRoleService.listByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return null;
        }
        List<String> roleIds = CollectionUtil.listToList(roles, SysRole::getId);
        List<SysMenu> sysMenus = listByRoleIds(roleIds);
        return sysMenuMapstruct.toDTOList(sysMenus);
    }

    /**
     * 根据用户id查询权限
     *
     * @param userId 用户id
     * @return 权限
     */
    public List<SysMenu> listByUserId(String userId) {
        List<SysRole> roles = sysRoleService.listByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return null;
        }
        List<String> roleIds = CollectionUtil.listToList(roles, SysRole::getId);
        return listByRoleIds(roleIds);
    }

    /**
     * 根据角色id查询权限
     *
     * @param roleIds 角色id
     * @return 权限
     */
    public List<SysMenu> listByRoleIds(List<String> roleIds) {
        return baseMapper.listByRoleIds(roleIds);
    }

    /**
     * 根据角色id查询权限
     *
     * @param roleId 角色id
     * @return 权限
     */
    public List<SysMenu> listByRoleId(String roleId) {
        return baseMapper.listByRoleIds(Collections.singletonList(roleId));
    }


    /**
     * 查询
     *
     * @param query .
     * @return .
     */
    public List<MenuDTO> list(MenuQuery query) {
        QueryWrapper<SysMenu> queryWrapper = QueryHelper.ofBean(query);
        queryWrapper = QueryHelper.order(queryWrapper, query);
        List<SysMenu> list = list(queryWrapper);
        return sysMenuMapstruct.toDTOList(list);
    }

    /**
     * 查询已启用的菜单与权限
     *
     * @return .
     */
    public List<MenuDTO> findAllEnabled() {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers
                .lambdaQuery(SysMenu.class)
                .eq(SysMenu::getEnabled, true)
                .orderByAsc(SysMenu::getRank);
        List<SysMenu> list = list(queryWrapper);
        return sysMenuMapstruct.toDTOList(list);
    }

    /**
     * 保存
     *
     * @param dto .
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(MenuSaveDTO dto) {
        SysMenu entity = sysMenuSaveMapstruct.toEntity(dto);
        save(entity);
    }

    /**
     * 更新
     *
     * @param dto .
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateById(MenuSaveDTO dto) {
        SysMenu saveData = sysMenuSaveMapstruct.toEntity(dto);
        if (null == saveData.getId()) {
            throw new ServiceException("id不能为空");
        }
        if (dto.getId().equals(dto.getParentId())) {
            throw new ServiceException("上级不能是自己");
        }
        SysMenu entity = getById(dto.getId());
        // 保证相关数据不会被修改
        BeanUtil.copyProperties(saveData, entity, CopyOptions.create().ignoreNullValue());
        updateById(entity);
    }

    /**
     * 删除
     *
     * @param id .
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        boolean hasChild = baseMapper.hasChild(id);
        if (hasChild) {
            throw new ServiceException("请先删除子节点");
        }
        // 与角色已绑定的权限不能删除
        boolean existed = baseMapper.existsRoleMenu(id);
        if (existed) {
            throw new ServiceException("已绑定角色，不能删除");
        }

        removeById(id);
    }
}
