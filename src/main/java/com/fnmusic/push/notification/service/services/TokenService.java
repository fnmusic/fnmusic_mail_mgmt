package com.fnmusic.push.notification.service.services;

import com.fnmusic.base.utils.ConstantUtils;
import com.fnmusic.push.notification.service.repository.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TokenService {

    @Autowired
    CacheRepository cacheRepository;

    static Object tokenCache;

    @PostConstruct
    public void init() {
        tokenCache = cacheRepository.createCache(ConstantUtils.APPNAME,"tokenCache",1L);
    }

    public Authentication retrieve(String token) {
        return (Authentication) cacheRepository.get(tokenCache,token);
    }

    public boolean contains(String token) {
        return retrieve(token) != null;
    }

    public boolean remove(String token) {
        return cacheRepository.remove(tokenCache,token);
    }


}
