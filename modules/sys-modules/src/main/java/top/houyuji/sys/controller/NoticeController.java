package top.houyuji.sys.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.houyuji.common.api.JsfPage;
import top.houyuji.common.base.R;
import top.houyuji.sys.domain.dto.NoticeDTO;
import top.houyuji.sys.domain.query.NoticeQuery;
import top.houyuji.sys.service.SysNoticeService;

import java.util.List;


@RestController
@RequestMapping("/sys/notice")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "系统公告管理")
public class NoticeController {
    private final SysNoticeService sysNoticeService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    @GetMapping
    @Operation(summary = "分页查询")
    public R<JsfPage<NoticeDTO>> page(NoticeQuery query) {
        JsfPage<NoticeDTO> res = sysNoticeService.page(query);
        return R.ok(res);
    }

    /**
     * 保存
     *
     * @param dto dto
     * @return 是否成功
     */
    @PostMapping
    @Operation(summary = "新增公告")
    public R<String> save(@Valid @RequestBody NoticeDTO dto) {
        sysNoticeService.save(dto);
        return R.ok();
    }

    /**
     * 更新公告
     *
     * @param dto .
     * @return .
     */
    @PutMapping
    @Operation(summary = "更新公告")
    public R<String> updateById(@Valid @RequestBody NoticeDTO dto) {
        sysNoticeService.updateById(dto);
        return R.ok();
    }

    /**
     * 根据ID删除公告
     *
     * @param ids .
     * @return .
     */
    @DeleteMapping
    @Operation(summary = "删除公告")
    public R<String> deleteByIds(@RequestBody List<String> ids) {
        sysNoticeService.deleteByIds(ids);
        return R.ok();
    }
}
