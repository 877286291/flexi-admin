package top.houyuji.sys.domain.vo;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.houyuji.common.satoken.domain.vo.TreeVO;

import java.util.List;

@Getter
@Setter
public class MenuTreeVO implements TreeVO<MenuTreeVO> {
    @Schema(description = "id")
    private String id;
    /**
     * 父类id
     */
    @Schema(description = "父类id")
    private String parentId;
    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String title;
    /**
     * 子类
     */
    @ArraySchema(schema = @Schema(description = "子类", implementation = Object.class, allOf = MenuTreeVO.class))
    private List<MenuTreeVO> children;
}