package top.houyuji.sys.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    private String userId;

    private String roleId;
}
