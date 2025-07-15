package top.houyuji.common.base.exception;

import lombok.*;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UsernameNotFoundException extends RuntimeException {
    /**
     * 错误码
     */
    @Getter
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    @Getter
    private String detailMessage;

    public UsernameNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public UsernameNotFoundException setMessage(String message) {
        this.message = message;
        return this;
    }

}