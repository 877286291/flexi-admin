package top.houyuji.sys.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserSaveDTO extends UserDTO {
    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<RoleSmallDTO> roles;
}
