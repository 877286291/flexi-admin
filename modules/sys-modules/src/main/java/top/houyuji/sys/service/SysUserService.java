package top.houyuji.sys.service;

import cn.dev33.satoken.stp.StpUtil;
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
import top.houyuji.sys.domain.dto.RoleSmallDTO;
import top.houyuji.sys.domain.dto.UserDTO;
import top.houyuji.sys.domain.dto.UserRestPwdDTO;
import top.houyuji.sys.domain.dto.UserSaveDTO;
import top.houyuji.sys.domain.entity.SysRole;
import top.houyuji.sys.domain.entity.SysUser;
import top.houyuji.sys.domain.query.UserQuery;
import top.houyuji.sys.mapper.SysUserMapper;
import top.houyuji.sys.service.mapstruct.SysUserMapstruct;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.houyuji.common.base.enums.ErrorCodeEnums.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserService extends BaseService<SysUserMapper, SysUser> {
    private final SysUserMapstruct sysUserMapstruct;
    private final SysRoleService sysRoleService;

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    public SysUser getByUsername(String username) {
        return baseMapper.getByUsername(username);
    }

    /**
     * 根据id查询用户
     *
     * @param id id
     * @return 用户
     */
    public SysUser getById(String id) {
        return baseMapper.selectById(id);
    }

    /**
     * 修改最后登录时间
     *
     * @param username 用户名
     */
    public void changeLastLoginTimeByUsername(String username) {
        baseMapper.changeLastLoginTimeByUsername(username);
    }


    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @param id       需要排除的id
     * @return 是否存在
     */
    public Boolean existsByUsername(String username, String id) {
        if (StrUtil.isBlank(id)) {
            return baseMapper.existsByUsername(username);
        }
        return baseMapper.existsByUsernameAndIdNot(username, id);
    }

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    public JsfPage<UserDTO> page(UserQuery query) {
        QueryWrapper<SysUser> queryWrapper = QueryHelper.ofBean(query);
        IPage<SysUser> page = QueryHelper.toPage(query);
        page = baseMapper.selectPage(page, queryWrapper);

        List<UserDTO> res = sysUserMapstruct.toDTOList(page.getRecords());
        return QueryHelper.toJsfPage(page, res);
    }

    /**
     * 查询
     *
     * @param query 查询条件
     * @return 数据
     */
    public List<UserDTO> list(UserQuery query) {
        QueryWrapper<SysUser> queryWrapper = QueryHelper.ofBean(query);
        List<SysUser> list = baseMapper.selectList(queryWrapper);
        return sysUserMapstruct.toDTOList(list);
    }

    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return 角色
     */
    public List<RoleSmallDTO> getRoles(String userId) {
        return baseMapper.getRolesByUserId(userId);
    }

    /**
     * 保存
     *
     * @param dto 数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(UserSaveDTO dto) {
        // 验证角色id是否存在
        checkRoleIdExist(dto);
        SysUser entity = sysUserMapstruct.saveDTOToEntity(dto);
        baseMapper.insert(entity);
        if (CollectionUtil.isNotEmpty(dto.getRoles())) {
            baseMapper.saveUserRole(entity.getId(), dto.getRoles().stream().map(RoleSmallDTO::getId).toList());
        }
    }

    /**
     * 更新
     *
     * @param dto 数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateById(UserSaveDTO dto) {
        SysUser sysUser = sysUserMapstruct.saveDTOToEntity(dto);
        if (StrUtil.isBlank(sysUser.getId())) {
            throw new ServiceException(USER_ID_REQUIRED);
        }
        SysUser entity = getById(sysUser.getId());
        if (entity == null) {
            throw new ServiceException(RECORD_NOT_FOUND);
        }

        // 账号不能修改，密码单独修改
        sysUser.setUsername(null);
        sysUser.setPassword(null);
        BeanUtil.copyProperties(sysUser, entity, CopyOptions.create().setIgnoreNullValue(true));
        baseMapper.updateById(entity);

        // 更新用户角色
        // 删除原有角色
        baseMapper.deleteUserRoleByUserId(entity.getId());
        // 保存新角色
        if (CollectionUtil.isNotEmpty(dto.getRoles())) {
            // 验证角色id是否存在
            checkRoleIdExist(dto);
            baseMapper.saveUserRole(entity.getId(), dto.getRoles().stream().map(RoleSmallDTO::getId).toList());
        }
    }

    /**
     * 检查传入的角色 ID 是否存在
     *
     * @param dto .
     */
    private void checkRoleIdExist(UserSaveDTO dto) {
        List<String> roleIds = dto.getRoles().stream().map(RoleSmallDTO::getId).toList();
        List<String> existRoleIds = sysRoleService.list().stream().map(SysRole::getId).toList();
        // 检查传入的角色 ID 是否全部存在
        Set<String> invalidRoleIds = roleIds.stream()
                .filter(roleId -> !existRoleIds.contains(roleId))
                .collect(Collectors.toSet());
        if (!invalidRoleIds.isEmpty()) {
            // 如果有无效的角色 ID，抛出异常或返回错误信息
            throw new ServiceException(ROLE_NOT_FOUND);
        }
    }

    /**
     * 删除
     *
     * @param id 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 踢人下线
        StpUtil.getTokenValueListByLoginId(id).forEach(StpUtil::kickoutByTokenValue);
        baseMapper.deleteById(id);
        // 删除用户角色
        baseMapper.deleteUserRoleByUserId(id);
        // 删除用户部门
        // 删除用户岗位
    }

    /**
     * 重置密码
     *
     * @param dto 数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(UserRestPwdDTO dto) {
        changePassword(dto.getId(), dto.getPassword());
    }

    /**
     * 修改密码
     *
     * @param id       id
     * @param password 密码
     */
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String id, String password) {
        SysUser entity = getById(id);
        if (entity == null) {
            throw new ServiceException(RECORD_NOT_FOUND);
        }
        entity.setPassword(password);
        baseMapper.updateById(entity);
    }
}
