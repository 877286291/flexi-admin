package top.houyuji.sys.service.mapstruct;

import top.houyuji.common.base.mapstruct.BaseMapstruct;
import top.houyuji.sys.domain.dto.RoleDTO;
import top.houyuji.sys.domain.entity.SysRole;


@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysRoleMapstruct extends BaseMapstruct<RoleDTO, SysRole> {
}
