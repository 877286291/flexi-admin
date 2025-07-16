package top.houyuji.sys.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.houyuji.common.base.R;
import top.houyuji.common.base.core.UserInfo;
import top.houyuji.common.base.utils.PasswordUtil;
import top.houyuji.common.cache.core.FlexiAdminCache;
import top.houyuji.sys.domain.dto.UserInfoDTO;
import top.houyuji.sys.domain.query.LoginQuery;
import top.houyuji.sys.domain.vo.LoginInfoVo;
import top.houyuji.sys.service.SysAuthService;
import top.houyuji.sys.service.mapstruct.SysAuthMapstruct;
import top.houyuji.utils.SaTokenUtil;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "授权管理")
@RequiredArgsConstructor
public class AuthController {
    private final SysAuthMapstruct sysAuthMapstruct;
    private final SysAuthService sysAuthService;
    private final FlexiAdminCache flexiAdminCache;

    /**
     * 登录
     *
     * @param query 登录信息
     * @return 登录信息
     */
    @PostMapping("/login")
    @Operation(summary = "登录")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "0",
                    description = "登录成功"
            )
    })
    public R<LoginInfoVo> loginByUsername(@Validated @RequestBody LoginQuery query) {
        // 公钥加密私钥解密
        String password = query.getPassword();
        String username = query.getUsername();
        UserInfoDTO user = sysAuthService.findByUsername(username);
        // 验证密码
        if (!PasswordUtil.matches(password, user.getPassword())) {
            return R.error("用户名或密码错误");
        }
        // 登录成功
        StpUtil.login(user.getId());
        SaSession saSession = StpUtil.getSession();
        saSession.set("userinfo", new UserInfo(user.getId(), user.getUsername()));
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        String token = tokenInfo.getTokenValue();
        // 返回用户信息
        LoginInfoVo loginInfoVo = sysAuthMapstruct.toDTO(user);
        loginInfoVo.setToken(token);
        return R.ok(loginInfoVo);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户信息")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "0",
                    description = "获取用户信息成功"
            ),
    })
    public R<LoginInfoVo> userInfo() {
        UserInfoDTO currentUser = SaTokenUtil.getCurrentUser();
        return R.ok(sysAuthMapstruct.toDTO(currentUser));
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public R<String> logout() {
        String loginId = StpUtil.getLoginIdAsString();
        flexiAdminCache.delete("user:" + loginId);
        StpUtil.logout();
        return R.ok();
    }
}
