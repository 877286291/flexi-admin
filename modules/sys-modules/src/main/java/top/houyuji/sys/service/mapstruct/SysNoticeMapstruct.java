package top.houyuji.sys.service.mapstruct;

import top.houyuji.common.base.mapstruct.BaseMapstruct;
import top.houyuji.sys.domain.dto.NoticeDTO;
import top.houyuji.sys.domain.entity.SysNotice;


@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysNoticeMapstruct extends BaseMapstruct<NoticeDTO, SysNotice> {
}
