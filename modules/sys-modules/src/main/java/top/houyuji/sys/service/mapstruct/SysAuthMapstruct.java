package top.houyuji.sys.service.mapstruct;

import org.mapstruct.Mapper;
import top.houyuji.common.base.mapstruct.BaseMapstruct;
import top.houyuji.sys.domain.dto.UserInfoDTO;
import top.houyuji.sys.domain.vo.LoginInfoVo;


@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysAuthMapstruct extends BaseMapstruct<LoginInfoVo, UserInfoDTO> {

}