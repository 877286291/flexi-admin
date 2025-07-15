package top.houyuji.sys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.houyuji.common.api.BaseQuery;
import top.houyuji.common.api.JsfPage;
import top.houyuji.common.base.R;
import top.houyuji.common.base.utils.CollectionUtil;
import top.houyuji.common.base.utils.PasswordUtil;
import top.houyuji.common.satoken.utils.SaTokenUtil;
import top.houyuji.sys.domain.dto.RoleSmallDTO;
import top.houyuji.sys.domain.dto.UserDTO;
import top.houyuji.sys.domain.dto.UserRestPwdDTO;
import top.houyuji.sys.domain.dto.UserSaveDTO;
import top.houyuji.sys.domain.entity.SysUser;
import top.houyuji.sys.domain.query.UserQuery;
import top.houyuji.sys.service.SysUserService;

import java.util.List;


@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
@Tag(name = "用户管理")
public class UserController {
    private final SysUserService sysUserService;

    /**
     * 分页查询用户
     *
     * @param query 查询条件
     * @return 用户分页数据
     */
    @GetMapping
    @Operation(summary = "分页查询用户")
    public R<JsfPage<UserDTO>> page(@Validated({BaseQuery.PageGroup.class}) UserQuery query) {
        JsfPage<UserDTO> res = sysUserService.page(query);
        // 忽略密码
        List<UserDTO> records = res.getRecords();
        if (CollectionUtil.isNotEmpty(records)) {
            records.forEach(UserDTO -> UserDTO.setPassword(null));
        }
        return R.ok(res);
    }

    /**
     * 查询用户列表
     *
     * @param query 查询条件
     * @return 用户列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询用户列表")
    public R<List<UserDTO>> list(@Validated UserQuery query) {
        List<UserDTO> res = sysUserService.list(query);
        // 忽略密码
        res.forEach(UserDTO -> UserDTO.setPassword(null));
        return R.ok(res);
    }

    /**
     * 用户名是否存在
     *
     * @param username .
     * @param id       .
     * @return .
     */
    @GetMapping("/existsByUsername")
    @Operation(summary = "用户名是否存在")
    @Parameters({
            @Parameter(name = "username", description = "用户名", required = true, example = "admin"),
            @Parameter(name = "id", description = "需要排除的用户ID", example = "1")
    })
    public R<Boolean> existsByUsernameAndIdNot(@RequestParam("username") String username,
                                               @RequestParam(name = "id", required = false) String id) {
        Boolean res = sysUserService.existsByUsername(username, id);
        return R.ok(res);
    }

    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return 角色
     */
    @GetMapping("/roles")
    @Operation(summary = "根据用户id查询角色")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID", required = true, example = "1")
    })
    public R<List<RoleSmallDTO>> getRolesByUserId(String userId) {
        List<RoleSmallDTO> res = sysUserService.getRoles(userId);
        return R.ok(res);
    }

    /**
     * 保存用户
     *
     * @param dto 用户信息
     * @return 是否成功
     */
    @PostMapping
    @Operation(summary = "保存用户")
    public R<String> save(@Validated({UserDTO.AddGroup.class}) @RequestBody UserSaveDTO dto) {
        dto.setPassword(PasswordUtil.encoder(dto.getPassword()));
        sysUserService.save(dto);
        return R.ok();
    }

    /**
     * 更新用户
     *
     * @param dto 用户信息
     * @return 是否成功
     */
    @PutMapping("/{userId}")
    @Operation(summary = "更新用户")
    public R<String> update(@PathVariable String userId, @RequestBody @Validated({UserDTO.UpdateGroup.class}) UserSaveDTO dto) {
        dto.setId(userId);
        dto.setPassword(null);
        sysUserService.updateById(dto);
        return R.ok();
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户")
    public R<String> delete(@PathVariable String userId) {
        sysUserService.deleteById(userId);
        return R.ok();
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    @PatchMapping("/{userId}/status/{status}")
    @Operation(summary = "更新用户状态")
    public R<String> updateStatus(@PathVariable String userId, @PathVariable Boolean status) {
        SysUser sysUser = sysUserService.getById(userId);
        if (sysUser == null) {
            return R.error("用户不存在");
        }
        sysUser.setEnabled(status);
        sysUserService.updateById(sysUser);
        return R.ok();
    }

    /**
     * 重置密码
     *
     * @param dto 数据
     * @return 是否成功
     */
    @PutMapping("/resetPassword")
    @Operation(summary = "重置密码")
    public R<String> resetPassword(@Validated({UserRestPwdDTO.RestPwdGroup.class}) @RequestBody UserRestPwdDTO dto) {
        String password = dto.getPassword();
        String pwd = PasswordUtil.encoder(password);
        dto.setPassword(pwd);
        String username = SaTokenUtil.getUsername();
        dto.setOperator(username);
        sysUserService.resetPassword(dto);
        return R.ok();
    }
}
