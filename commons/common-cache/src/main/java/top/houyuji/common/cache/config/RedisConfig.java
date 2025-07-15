package top.houyuji.common.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@ConditionalOnProperty(name = "flexi.admin.cache.enabled", havingValue = "true", matchIfMissing = true)
public class RedisConfig {

    private static RedisTemplate<String, Object> getStringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<?> stringSerializer, Jackson2JsonRedisSerializer<Object> serializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        {
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            redisTemplate.setKeySerializer(stringSerializer);     // key序列化
            redisTemplate.setValueSerializer(serializer);         // value序列化
            redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
            redisTemplate.setHashValueSerializer(serializer);     // Hash value序列化
            redisTemplate.afterPropertiesSet();
        }
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 序列化规则
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        // value序列化
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        // key序列化
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();

        // 配置redisTemplate
        return getStringObjectRedisTemplate(redisConnectionFactory, stringSerializer, serializer);
    }
}