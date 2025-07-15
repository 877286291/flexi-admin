package top.houyuji.sys.domain.query;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.houyuji.common.api.BaseQuery;
import top.houyuji.common.query.annotation.Equals;
import top.houyuji.common.query.annotation.Like;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RoleQuery extends BaseQuery {
    /**
     * 角色名称
     */
    @Like
    @Parameter(description = "角色名称")
    private String name;
    /**
     * 角色编码
     */
    @Equals
    @Parameter(description = "角色编码")
    private String code;
    /**
     * 是否启用
     */
    @Parameter(description = "是否启用")
    @Equals
    private Boolean enabled;
}
