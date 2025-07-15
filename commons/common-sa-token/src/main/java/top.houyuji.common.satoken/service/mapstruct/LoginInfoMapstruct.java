package top.houyuji.common.satoken.service.mapstruct;

import org.mapstruct.Mapper;
import top.houyuji.common.base.mapstruct.BaseMapstruct;
import top.houyuji.common.satoken.domain.dto.UserInfoDTO;
import top.houyuji.common.satoken.domain.vo.LoginInfoVo;


@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface LoginInfoMapstruct extends BaseMapstruct<LoginInfoVo, UserInfoDTO> {

}