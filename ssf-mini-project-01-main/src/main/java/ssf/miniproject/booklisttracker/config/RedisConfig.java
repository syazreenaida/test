package ssf.miniproject.booklisttracker.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import ssf.miniproject.booklisttracker.model.MoodBook;

@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> createRedisTemplate() {
       
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        
        String redisPassword = System.getenv("REDIS_PASSWORD");

        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setPassword("TAews8zb5IdVkIs6Ou2h0SKfmjIwc51Y");
        Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(MoodBook.class);

        

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        logger.info("redis host port > {} {}", redisHost, redisPort.get());
        
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        template.setValueSerializer(serializer);
        
        return template;
        
    }
}