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
public class ProductQuery extends BaseQuery {
    @Like
    @Parameter(description = "名称")
    private String name;
    @Equals
    @Parameter(description = "是否启用")
    private Boolean enabled;
}
