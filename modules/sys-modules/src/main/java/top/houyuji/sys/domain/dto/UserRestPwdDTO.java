package top.houyuji.sys.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class UserRestPwdDTO implements Serializable {
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户ID不能为空", groups = {RestPwdGroup.class})
    private String id;
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空", groups = {RestPwdGroup.class})
    private String password;
    @Schema(description = "操作人", hidden = true)
    private String operator;

    public interface RestPwdGroup {
    }
}
