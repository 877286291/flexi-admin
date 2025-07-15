package top.houyuji.common.base.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class UserInfo implements Serializable {
    private String id;
    private String username;

    public UserInfo(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserInfo() {
    }
}
