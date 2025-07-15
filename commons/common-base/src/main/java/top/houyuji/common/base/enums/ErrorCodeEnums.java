package top.houyuji.common.base.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnums {
    // 系统 1001-1099
    RECORD_NOT_FOUND(1001, "记录不存在"),
    USER_NOT_LOGIN(1002, "用户未登录"),
    PERMISSION_DENIED(1003, "访问权限不足"),
    // 用户 1101-1199
    USER_ID_REQUIRED(1101, "用户id不能为空"),
    USER_NOT_FOUND(1102, "用户不存在"),
    // 角色 1201-1299
    ROLE_NOT_FOUND(1201, "角色不存在"),
    ROLE_IS_BINDING(1202, "角色已被绑定无法删除"),
    ROLE_CODE_EXISTS(1203, "角色编码已存在"),
    // 权限 1301-1399
    ;
    private final Integer code;
    private final String message;

    ErrorCodeEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
