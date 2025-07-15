package top.houyuji.common.satoken.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserInfoDTO {
    /**
     * 用户ID
     */
    private String id;
    /**
     * 用户
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 机构信息
     */
    private UserOrgInfoDTO orgInfo;
    /**
     * 用户角色
     */
    private List<String> roles;
    /**
     * 用户权限
     */
    private List<String> permissions;
    /**
     * 是否启用
     */
    private Boolean enabled;

}