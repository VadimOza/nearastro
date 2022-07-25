package com.nearastro.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class NearAstroConfiguraiton {

    @Bean
    ObjectMapper mapper() {
        var mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }

    @Bean
    ReactiveHashOperations<String, String, JsonNode> redisOperations(ReactiveRedisConnectionFactory factory, ObjectNodeSerializer objectNodeSerializer) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        RedisSerializationContext.RedisSerializationContextBuilder<String, ObjectNode> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);

        RedisSerializationContext<String, ObjectNode> context = builder
                .key(keySerializer)
                .hashValue(objectNodeSerializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context).opsForHash();
    }

}
