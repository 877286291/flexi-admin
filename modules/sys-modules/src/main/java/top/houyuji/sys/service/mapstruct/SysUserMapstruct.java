package top.houyuji.sys.service.mapstruct;

import top.houyuji.common.base.mapstruct.BaseMapstruct;
import top.houyuji.sys.domain.dto.UserDTO;
import top.houyuji.sys.domain.dto.UserSaveDTO;
import top.houyuji.sys.domain.entity.SysUser;


@org.mapstruct.Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysUserMapstruct extends BaseMapstruct<UserDTO, SysUser> {

    /**
     * dto转entity
     *
     * @param dto dto
     * @return entity
     */
    SysUser saveDTOToEntity(UserSaveDTO dto);
}
