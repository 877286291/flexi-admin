package top.houyuji.sys.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.houyuji.common.mybatis.core.domain.BaseEntity;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends BaseEntity {
    /**
     * 角色名称
     */
    @Schema(description = "id")
    private String id;
    /**
     * 角色名称
     */
    @Schema(description = "角色名称", example = "test")
    private String name;
    /**
     * 角色标识
     */
    @Schema(description = "角色标识", example = "test")
    private String code;
    /**
     * 描述
     */
    @Schema(description = "描述", example = "这是一个测试角色")
    private String description;
    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enabled;

    /**
     * 权限id
     */
    @Schema(description = "权限id")
    private List<String> permissionIds;
}
