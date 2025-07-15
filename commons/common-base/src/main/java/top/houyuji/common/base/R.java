package top.houyuji.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.houyuji.common.base.enums.ErrorCodeEnums;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 统一响应结果封装类
 *
 * @param <T> 数据类型
 * @author houyuji
 */
@Slf4j
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "统一响应结果")
public class R<T> implements Serializable {

    // ==================== 常量定义 ====================
    /**
     * 业务成功状态码
     */
    public static final Integer SUCCESS_CODE = 0;
    /**
     * 业务失败状态码
     */
    public static final Integer ERROR_CODE = -1;
    /**
     * 系统错误状态码
     */
    public static final Integer SYSTEM_ERROR_CODE = 500;
    @Serial
    private static final long serialVersionUID = 1L;

    // ==================== 响应字段 ====================
    /**
     * 业务状态码
     */
    @Schema(description = "业务状态码", example = "0")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息", example = "操作成功")
    private String message;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 请求是否成功
     */
    @Schema(description = "请求是否成功", example = "true")
    private Boolean success;

    /**
     * 响应时间戳
     */
    @Schema(description = "响应时间戳")
    private LocalDateTime timestamp;
    // ==================== 构造方法 ====================

    /**
     * 私有构造方法
     */
    private R() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 私有构造方法
     */
    private R(Integer code, String message, T data, Boolean success) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    // ==================== 成功响应静态方法 ====================

    /**
     * 成功响应（无数据）
     */
    public static <T> R<T> ok() {
        return new R<>(SUCCESS_CODE, "操作成功", null, true);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> R<T> ok(T data) {
        return new R<>(SUCCESS_CODE, "操作成功", data, true);
    }

    /**
     * 成功响应（自定义消息和数据）
     */
    public static <T> R<T> ok(String message, T data) {
        return new R<>(SUCCESS_CODE, message, data, true);
    }

    /**
     * 成功响应（自定义状态码、消息和数据）
     */
    public static <T> R<T> ok(Integer code, String message, T data) {
        return new R<>(code, message, data, true);
    }

    // ==================== 失败响应静态方法 ====================

    /**
     * 失败响应（默认消息）
     */
    public static <T> R<T> error() {
        return new R<>(ERROR_CODE, "操作失败", null, false);
    }

    /**
     * 失败响应（自定义消息）
     */
    public static <T> R<T> error(String message) {
        return new R<>(ERROR_CODE, message, null, false);
    }

    /**
     * 失败响应（自定义状态码和消息）
     */
    public static <T> R<T> error(Integer code, String message) {
        return new R<>(code, message, null, false);
    }

    /**
     * 失败响应（错误枚举）
     */
    public static <T> R<T> error(ErrorCodeEnums errorCode) {
        return new R<>(errorCode.getCode(), errorCode.getMessage(), null, false);
    }

    /**
     * 失败响应（错误枚举和数据）
     */
    public static <T> R<T> error(ErrorCodeEnums errorCode, T data) {
        return new R<>(errorCode.getCode(), errorCode.getMessage(), data, false);
    }

    // ==================== 系统错误响应静态方法 ====================

    /**
     * 系统错误响应
     */
    public static <T> R<T> systemError() {
        return new R<>(SYSTEM_ERROR_CODE, "系统内部错误", null, false);
    }

    /**
     * 系统错误响应（自定义消息）
     */
    public static <T> R<T> systemError(String message) {
        return new R<>(SYSTEM_ERROR_CODE, message, null, false);
    }

    // ==================== 条件响应静态方法 ====================

    /**
     * 根据条件返回成功或失败
     */
    public static <T> R<T> result(boolean condition) {
        return condition ? ok() : error();
    }

    /**
     * 根据条件返回成功或失败（带数据）
     */
    public static <T> R<T> result(boolean condition, T data) {
        return condition ? ok(data) : error();
    }

    /**
     * 根据条件返回成功或失败（自定义消息）
     */
    public static <T> R<T> result(boolean condition, String successMsg, String errorMsg) {
        return condition ? ok(successMsg, null) : error(errorMsg);
    }
}