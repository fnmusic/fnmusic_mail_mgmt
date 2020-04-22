package com.fnmusic.push.notification.service.repository;

import com.fnmusic.base.repository.impl.AbstractCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository extends AbstractCacheRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void init() {
        this.abstractRedisTemplate = redisTemplate;
    }
}
