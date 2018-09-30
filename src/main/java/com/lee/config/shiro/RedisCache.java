package com.lee.config.shiro;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache<K, V> implements Cache<K, V> {

    private final static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    public RedisCache() {
        super();
    }

    @Override
    public V get(K k) throws CacheException {
        return redisTemplate.opsForValue().get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(k, v, 5L, TimeUnit.MINUTES);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = redisTemplate.opsForValue().get(k);
        redisTemplate.delete(k);
        return v;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
