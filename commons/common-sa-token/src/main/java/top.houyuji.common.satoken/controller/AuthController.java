package top.houyuji.common.satoken.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import top.houyuji.common.satoken.domain.LoginRequest;
import top.houyuji.common.satoken.domain.dto.UserInfoDTO;
import top.houyuji.common.satoken.domain.vo.LoginInfoVo;
import top.houyuji.common.satoken.service.UserLoginService;
import top.houyuji.common.satoken.service.mapstruct.LoginInfoMapstruct;
import top.houyuji.common.satoken.utils.SaTokenUtil;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "授权")
@RequiredArgsConstructor
public class AuthController {
    private final LoginInfoMapstruct loginInfoMapstruct;
    private final UserLoginService userLoginService;
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
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "登录异常",
                    content = @Content(schema = @Schema(implementation = R.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "认证失败",
                    content = @Content(schema = @Schema(implementation = R.class))
            )
    })
    public R<LoginInfoVo> loginByUsername(@Validated @RequestBody LoginRequest query) {
        // 公钥加密私钥解密
        String password = query.getPassword();
        String username = query.getUsername();
        UserInfoDTO user = userLoginService.findByUsername(username);
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
        LoginInfoVo res = loginInfoMapstruct.toDTO(user);
        res.setToken(token);
        return R.ok(res);
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
            @ApiResponse(
                    responseCode = "500",
                    description = "获取用户信息异常",
                    content = @Content(schema = @Schema(implementation = R.class))
            )
    })
    public R<LoginInfoVo> userInfo() {
        UserInfoDTO currentUser = SaTokenUtil.getCurrentUser();
        return R.ok(loginInfoMapstruct.toDTO(currentUser));
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
