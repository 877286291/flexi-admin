package top.houyuji.sys.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.houyuji.common.base.annotation.ValidNumber;
import top.houyuji.common.base.enums.GenderEnums;
import top.houyuji.common.base.enums.ValueEnum;
import top.houyuji.common.mybatis.core.domain.BaseEntity;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseEntity {
    private String id;
    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "admin")
    @NotBlank(message = "不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码", example = "123456")
    @NotBlank(message = "不能为空", groups = {AddGroup.class})
    private String password;
    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "Aurora")
    @NotBlank(message = "不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String nickname;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "example@example.com")
    private String email;
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800000000")
    private String phone;
    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;
    /**
     * 性别
     */
    @Schema(description = "性别,0 保密,1 男,2 女", example = "0")
    @ValidNumber(values = {0, 1, 2}, groups = {AddGroup.class, UpdateGroup.class})
    private Integer gender;
    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;
    /**
     * 最近修改密码时间
     */
    @Schema(description = "最近修改密码时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pwdResetTime;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Boolean enabled;

    @Schema(description = "性别名称")
    public String getGenderName() {
        GenderEnums valueEnum = ValueEnum.valueToEnum(GenderEnums.class, gender, GenderEnums.UNKNOWN);
        return valueEnum.getName();

    }

    public interface AddGroup {
    }

    public interface UpdateGroup {
    }
}
