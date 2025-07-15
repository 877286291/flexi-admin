package top.houyuji.sys.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.houyuji.common.mybatis.core.domain.BaseEntity;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
@TableName("sys_user")
public class SysUser extends BaseEntity {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 性别 0 未知 1 男 2 女
     */
    private Integer gender = 0;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后登录ip
     */
    private String lastLoginIp;
    /**
     * 最后修改密码的时间
     */
    private Date lastPwdResetTime;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    @TableField("`is_enabled`")
    private Boolean enabled = true;
}
