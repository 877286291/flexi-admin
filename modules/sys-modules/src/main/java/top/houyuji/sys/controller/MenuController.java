package top.houyuji.sys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.houyuji.common.base.R;
import top.houyuji.common.base.utils.StrUtil;
import top.houyuji.sys.domain.dto.MenuDTO;
import top.houyuji.sys.domain.dto.MenuSaveDTO;
import top.houyuji.sys.domain.dto.UserInfoDTO;
import top.houyuji.sys.domain.query.MenuQuery;
import top.houyuji.sys.domain.vo.MenuTreeVO;
import top.houyuji.sys.domain.vo.RouteVO;
import top.houyuji.sys.service.SysMenuService;
import top.houyuji.utils.MenuUtil;
import top.houyuji.utils.SaTokenUtil;

import java.util.List;


@RestController
@RequestMapping("/sys/menus")
@Tag(name = "菜单管理")
@RequiredArgsConstructor
public class MenuController {
    private final SysMenuService sysMenuService;

    /**
     * 获取用户路由
     */
    @GetMapping("/routes")
    @Operation(summary = "获取用户路由")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "获取用户路由成功"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "获取用户路由异常",
                    content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = R.class))
            )
    })
    public R<List<RouteVO>> routes() {
        UserInfoDTO currentUser = SaTokenUtil.getCurrentUser();
        if (currentUser == null) {
            return R.error("获取当前用户失败");
        }
        return R.ok(sysMenuService.getRoutes(currentUser.getId()));
    }

    /**
     * 查询
     *
     * @param query .
     * @return .
     */
    @GetMapping
    @Operation(summary = "列表查询", description = "默认查询root节点，rank排序")
    public R<List<MenuDTO>> list(@Validated MenuQuery query) {
        List<MenuDTO> res = sysMenuService.list(query);
        return R.ok(res);
    }

    /**
     * 菜单树
     *
     * @param filterButton .
     * @return .
     */
    @GetMapping("/tree")
    @Operation(summary = "菜单树")
    @Parameters({
            @Parameter(name = "filterButton", description = "是否过滤按钮")
    })
    public R<List<MenuTreeVO>> menuTree(@RequestParam(required = false, defaultValue = "true", name = "filterButton") Boolean filterButton) {
        List<MenuDTO> allEnabled = sysMenuService.findAllEnabled();
        List<MenuTreeVO> res = MenuUtil.buildMenuTree(allEnabled, filterButton);
        return R.ok(res);
    }

    /**
     * 添加
     *
     * @param dto .
     * @return .
     */
    @PostMapping
    @Operation(summary = "添加菜单")
    public R<String> save(@Valid @RequestBody MenuSaveDTO dto) {
        dto.setId(null);
        // 将空字符串转为null
        if (StrUtil.isNotBlank(dto.getParentId())
                && "0".equals(dto.getParentId())) {
            dto.setParentId(null);
        }
        if ("".equals(dto.getParentId())) {
            dto.setParentId(null);
        }
        sysMenuService.save(dto);
        return R.ok();
    }

    /**
     * 修改
     *
     * @param dto .
     * @return .
     */
    @PutMapping
    @Operation(summary = "修改菜单")
    public R<String> update(@Validated @RequestBody MenuSaveDTO dto) {
        String id = dto.getId();
        if (StrUtil.isBlank(id)) {
            return R.error("id不能为空");
        }
        if (StrUtil.isNotBlank(dto.getParentId()) && id.equals(dto.getParentId())) {
            return R.error("上级不能是自己");
        }
        // 将空字符串转为null
        if (StrUtil.isNotBlank(dto.getParentId()) && "0".equals(dto.getParentId())) {
            dto.setParentId(null);
        }
        if ("".equals(dto.getParentId())) {
            dto.setParentId(null);
        }
        sysMenuService.updateById(dto);
        return R.ok();
    }

    /**
     * 删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping
    @Operation(summary = "删除菜单")
    @Parameters({
            @Parameter(name = "id", description = "id", required = true)
    })
    public R<String> delete(@RequestParam(name = "id") String id) {
        sysMenuService.deleteById(id);
        return R.ok();
    }
}
