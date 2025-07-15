package top.houyuji.sys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.houyuji.common.base.R;
import top.houyuji.common.base.utils.StrUtil;
import top.houyuji.sys.domain.dto.PermissionDTO;
import top.houyuji.sys.domain.dto.PermissionSaveDTO;
import top.houyuji.sys.domain.query.PermissionQuery;
import top.houyuji.sys.domain.vo.MenuTreeVO;
import top.houyuji.sys.service.SysPermissionService;
import top.houyuji.utils.MenuUtil;

import java.util.List;


@RestController
@RequestMapping("/sys/permission")
@Tag(name = "菜单与权限")
@RequiredArgsConstructor
public class PermissionController {
    private final SysPermissionService sysPermissionService;

    /**
     * 查询
     *
     * @param query .
     * @return .
     */
    @GetMapping
    @Operation(summary = "列表查询", description = "默认查询root节点，rank排序")
    public R<List<PermissionDTO>> list(@Validated PermissionQuery query) {
        List<PermissionDTO> res = sysPermissionService.list(query);
        return R.ok(res);
    }

    /**
     * 菜单树
     *
     * @param filterButton .
     * @return .
     */
    @GetMapping("/menu/tree")
    @Operation(summary = "菜单树")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "filterButton", description = "是否过滤按钮", required = false)
    })
    public R<List<MenuTreeVO>> menuTree(@RequestParam(required = false, defaultValue = "false", name = "filterButton") Boolean filterButton) {
        List<PermissionDTO> allEnabled = sysPermissionService.findAllEnabled();
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
    @Operation(summary = "添加")
    public R<String> save(@Valid @RequestBody PermissionSaveDTO dto) {
        dto.setId(null);
        // 将空字符串转为null
        if (StrUtil.isNotBlank(dto.getParentId())
                && "0".equals(dto.getParentId())) {
            dto.setParentId(null);
        }
        if ("".equals(dto.getParentId())) {
            dto.setParentId(null);
        }
        sysPermissionService.save(dto);
        return R.ok();
    }

    /**
     * 修改
     *
     * @param dto .
     * @return .
     */
    @PutMapping
    @Operation(summary = "修改")
    public R<String> update(@Validated @RequestBody PermissionSaveDTO dto) {
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
        sysPermissionService.updateById(dto);
        return R.ok();
    }

    /**
     * 删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping
    @Operation(summary = "删除")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "id", required = true)
    })
    public R<String> delete(@RequestParam(name = "id") String id) {
        sysPermissionService.deleteById(id);
        return R.ok();
    }
}
