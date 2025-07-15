package top.houyuji.common.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "flexi.admin.cache")
@Setter
@Getter
public class CacheProperties {
    /**
     * 是否启用缓存
     */
    private boolean enabled = true;
    /**
     * 缓存前缀
     */
    private String prefix = "flexi_admin";
}