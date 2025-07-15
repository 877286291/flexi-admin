package top.houyuji.sys.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class RoleSmallDTO implements Serializable {
    @Schema(description = "角色ID")
    private String id;
    @Schema(description = "角色代码")
    private String code;
    @Schema(description = "角色名称")
    private String name;
}
