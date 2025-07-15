package top.houyuji.sys.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.houyuji.common.api.JsfPage;
import top.houyuji.common.base.exception.ServiceException;
import top.houyuji.common.base.utils.CollectionUtil;
import top.houyuji.common.base.utils.StrUtil;
import top.houyuji.common.query.mybatis.plus.QueryHelper;
import top.houyuji.sys.domain.dto.NoticeDTO;
import top.houyuji.sys.domain.entity.SysNotice;
import top.houyuji.sys.domain.query.NoticeQuery;
import top.houyuji.sys.mapper.SysNoticeMapper;
import top.houyuji.sys.service.mapstruct.SysNoticeMapstruct;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SysNoticeService extends ServiceImpl<SysNoticeMapper, SysNotice> {
    private final SysNoticeMapstruct sysNoticeMapstruct;

    /**
     * 统计不在ids中的数量
     *
     * @param ids ids
     * @return 数量
     */
    public Integer countNotInIds(List<String> ids) {
        LambdaQueryWrapper<SysNotice> eq = Wrappers.lambdaQuery(SysNotice.class)
                .notIn(SysNotice::getId, ids)
                .eq(SysNotice::getEnabled, true);
        long count = count(eq);
        return (int) count;
    }

    /**
     * 查询不在ids中的id
     *
     * @param ids ids
     * @return id
     */
    public List<String> getIdsNotInIds(List<String> ids) {
        LambdaQueryWrapper<SysNotice> eq = Wrappers.lambdaQuery(SysNotice.class)
                .notIn(SysNotice::getId, ids)
                .eq(SysNotice::getEnabled, true)
                .select(SysNotice::getId);
        List<SysNotice> list = list(eq);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return CollectionUtil.listToList(list, SysNotice::getId);
    }

    /**
     * 统计公告数量
     *
     * @return 数量
     */
    public int countNotices() {
        LambdaQueryWrapper<SysNotice> eq = Wrappers.lambdaQuery(SysNotice.class)
                .eq(SysNotice::getEnabled, true);
        long count = count(eq);
        return (int) count;
    }

    /**
     * 查询所有id
     *
     * @return id
     */
    public List<String> getIds() {
        LambdaQueryWrapper<SysNotice> eq = Wrappers.lambdaQuery(SysNotice.class)
                .eq(SysNotice::getEnabled, true)
                .select(SysNotice::getId);
        List<SysNotice> list = list(eq);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return CollectionUtil.listToList(list, SysNotice::getId);
    }


    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    public JsfPage<NoticeDTO> page(NoticeQuery query) {
        QueryWrapper<SysNotice> queryWrapper = QueryHelper.ofBean(query);
        IPage<SysNotice> page = QueryHelper.toPage(query);
        page = page(page, queryWrapper);
        List<NoticeDTO> res = sysNoticeMapstruct.toDTOList(page.getRecords());
        // 获取产品对应的权限


        return QueryHelper.toJsfPage(page, res);
    }

    /**
     * 查询
     *
     * @param query 查询条件
     * @return 数据
     */
    public List<NoticeDTO> list(NoticeQuery query) {
        QueryWrapper<SysNotice> queryWrapper = QueryHelper.ofBean(query);
        List<SysNotice> list = list(queryWrapper);
        return sysNoticeMapstruct.toDTOList(list);
    }

    /**
     * 保存
     *
     * @param dto dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(NoticeDTO dto) {
        SysNotice entity = sysNoticeMapstruct.toEntity(dto);
        save(entity);
    }

    /**
     * 更新
     *
     * @param dto dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateById(NoticeDTO dto) {
        String id = dto.getId();
        if (StrUtil.isBlank(id)) {
            throw new ServiceException("id不能为空");
        }
        SysNotice sysNotice = baseMapper.selectById(id);
        BeanUtil.copyProperties(
                dto,
                sysNotice,
                CopyOptions.create().ignoreNullValue()
        );
        updateById(sysNotice);
    }

    /**
     * 通过id删除
     *
     * @param ids id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return;
        }
        removeByIds(ids);
    }

    /**
     * 通过id关闭
     *
     * @param ids id
     */
    @Transactional(rollbackFor = Exception.class)
    public void closeByIds(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return;
        }
        LambdaQueryWrapper<SysNotice> eq = Wrappers.lambdaQuery(SysNotice.class)
                .in(SysNotice::getId, ids)
                .eq(SysNotice::getEnabled, true);
        List<SysNotice> list = list(eq);
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        list.forEach(sysNotice -> sysNotice.setEnabled(false));
        updateBatchById(list);
    }
}
