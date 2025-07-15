package top.houyuji.common.base.exception;

import lombok.*;
import top.houyuji.common.base.R;
import top.houyuji.common.base.enums.ErrorCodeEnums;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    /**
     * 错误码
     */
    @Getter
    private Integer code = R.SYSTEM_ERROR_CODE;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    @Getter
    private String detailMessage;

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(ErrorCodeEnums enums) {
        this.code = enums.getCode();
        this.message = enums.getMessage();
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}