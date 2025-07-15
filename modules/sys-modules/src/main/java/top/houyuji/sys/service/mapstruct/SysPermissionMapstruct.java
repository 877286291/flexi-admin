package top.houyuji.sys.service.mapstruct;

import top.houyuji.common.base.mapstruct.BaseMapstruct;
import top.houyuji.sys.domain.dto.PermissionDTO;
import top.houyuji.sys.domain.entity.SysPermission;


@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysPermissionMapstruct extends BaseMapstruct<PermissionDTO, SysPermission> {

}
