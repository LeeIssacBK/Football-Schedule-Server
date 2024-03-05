package com.fs.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisProvider {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValue(String key, Object value, long ttl) {
        if (ttl > -1) {
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    public void deleteValue(String key, boolean isRelationValue) {
        if (isRelationValue) {
            redisTemplate.execute((RedisCallback<?>) conn -> {
                Cursor<byte[]> cursor = conn.scan(ScanOptions.scanOptions()
                    .match(key + "?*")
                    .count(10)
                    .build());
                while (cursor.hasNext()) {
                    String relationKey = new String(cursor.next(), StandardCharsets.UTF_8);
                    redisTemplate.delete(relationKey);
                }
                cursor.close();
                return null;
            });
        } else {
            redisTemplate.delete(key);
        }
    }

}
