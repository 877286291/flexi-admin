package top.houyuji.sys.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.houyuji.common.api.JsfPage;
import top.houyuji.common.base.exception.ServiceException;
import top.houyuji.common.base.utils.CollectionUtil;
import top.houyuji.common.base.utils.StrUtil;
import top.houyuji.common.mybatis.core.service.BaseService;
import top.houyuji.common.query.mybatis.plus.QueryHelper;
import top.houyuji.sys.domain.dto.RoleDTO;
import top.houyuji.sys.domain.entity.SysRole;
import top.houyuji.sys.domain.query.RoleQuery;
import top.houyuji.sys.mapper.SysRoleMapper;
import top.houyuji.sys.service.mapstruct.SysRoleMapstruct;

import java.util.List;

import static top.houyuji.common.base.enums.ErrorCodeEnums.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleService extends BaseService<SysRoleMapper, SysRole> {
    private final SysRoleMapstruct sysRoleMapstruct;

    /**
     * 编码是否存在
     *
     * @param code 编码
     * @param id   需要排除的id
     * @return 是否存在
     */
    public Boolean existsCode(String code, String id) {
        if (StrUtil.isNotBlank(id)) {
            return baseMapper.existsCodeByNotId(code, id);
        }
        return baseMapper.existsCode(code);
    }

    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return 角色
     */
    public List<SysRole> listByUserId(String userId) {
        return baseMapper.listByUserId(userId);
    }

    /**
     * 查询
     *
     * @param query 查询条件
     * @return 角色列表
     */
    public List<RoleDTO> list(RoleQuery query) {
        QueryWrapper<SysRole> queryWrapper = QueryHelper.ofBean(query);
        List<SysRole> list = list(queryWrapper);
        return sysRoleMapstruct.toDTOList(list);
    }

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 角色列表
     */
    public JsfPage<RoleDTO> page(RoleQuery query) {
        QueryWrapper<SysRole> queryWrapper = QueryHelper.ofBean(query);
        IPage<SysRole> page = QueryHelper.toPage(query);
        page = page(page, queryWrapper);
        List<RoleDTO> res = sysRoleMapstruct.toDTOList(page.getRecords());
        return QueryHelper.toJsfPage(page, res);
    }

    /**
     * 保存
     *
     * @param dto dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(RoleDTO dto) {
        SysRole entity = sysRoleMapstruct.toEntity(dto);
        Boolean res = existsCode(entity.getCode(), entity.getId());
        if (res) {
            throw new ServiceException(ROLE_CODE_EXISTS);
        }
        save(entity);
    }

    /**
     * 更新
     *
     * @param dto dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateById(RoleDTO dto) {
        SysRole role = sysRoleMapstruct.toEntity(dto);
        role.setCode(null);
        SysRole entity = getById(role.getId());
        if (entity == null) {
            throw new ServiceException(RECORD_NOT_FOUND);
        }
        BeanUtil.copyProperties(role, entity, CopyOptions.create().setIgnoreNullValue(true));
        updateById(entity);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 是否有用户关联
        boolean exists = baseMapper.existsUserByRoleId(id);
        if (exists) {
            throw new ServiceException(ROLE_IS_BINDING);
        }
        removeById(id);
        // 删除角色菜单
        baseMapper.deleteRoleMenu(id);
    }

    /**
     * 获取权限id
     *
     * @param roleId 角色id
     * @return 权限id
     */
    public List<String> getPermissionIds(String roleId) {
        return baseMapper.getPermissionIdsByRoleId(roleId);
    }

    /**
     * 分配菜单
     *
     * @param roleId  角色id
     * @param menuIds 菜单id
     */
    @Transactional(rollbackFor = Exception.class)
    public void assign(String roleId, List<String> menuIds) {
        baseMapper.deleteRoleMenu(roleId);
        if (CollectionUtil.isNotEmpty(menuIds)) {
            baseMapper.insertRoleMenu(roleId, menuIds);
        }
    }
}
