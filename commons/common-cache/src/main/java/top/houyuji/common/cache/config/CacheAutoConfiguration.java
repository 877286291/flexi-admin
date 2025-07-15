package top.houyuji.common.cache.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.houyuji.common.cache.core.FlexiAdminCache;
import top.houyuji.common.cache.core.ICache;


@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "flexi.admin.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CacheAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public FlexiAdminCache cache(
            ICache redisCache,
            CacheProperties cacheProperties) {
        FlexiAdminCache flexiAdminCache = new FlexiAdminCache();
        flexiAdminCache.setCache(redisCache);
        flexiAdminCache.setPrefix(cacheProperties.getPrefix());
        return flexiAdminCache;
    }
}