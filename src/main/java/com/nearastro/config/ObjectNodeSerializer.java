package com.nearastro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ObjectNodeSerializer implements RedisSerializer<ObjectNode> {

    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public byte[] serialize(ObjectNode jsonNodes) throws SerializationException {
        return mapper.writeValueAsBytes(jsonNodes);
    }

    @SneakyThrows
    @Override
    public ObjectNode deserialize(byte[] bytes) throws SerializationException {
        return (ObjectNode) mapper.readTree(bytes);
    }
}
