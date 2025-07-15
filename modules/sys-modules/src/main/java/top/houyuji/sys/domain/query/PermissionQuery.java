package top.houyuji.sys.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class PermissionQuery extends BaseQuery {
    /**
     * 菜单名称
     */
    @Like
    @Schema(description = "菜单名称")
    private String title;

    /**
     * 父级id
     */
    @Equals(allowNull = true)
    @Schema(description = "父级id")
    private String parentId;
}
