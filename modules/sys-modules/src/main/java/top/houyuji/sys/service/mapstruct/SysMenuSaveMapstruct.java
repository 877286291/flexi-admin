package top.houyuji.sys.service.mapstruct;

import top.houyuji.common.base.mapstruct.BaseMapstruct;
import top.houyuji.sys.domain.dto.MenuSaveDTO;
import top.houyuji.sys.domain.entity.SysMenu;


@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysMenuSaveMapstruct extends BaseMapstruct<MenuSaveDTO, SysMenu> {

}
