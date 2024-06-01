package com.nhnacademy.restsecurity.service;

import com.nhnacademy.restsecurity.request.LoginFailCountRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
public class LoginService {
    private final String HASH_LOGIN_COUNT = "LoginFailCount:";
    private final String HASH_LOGIN_LOCK = "LoginLock:";

    private final RedisTemplate<String, Object> redisTemplate;

    public LoginService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void initializeLoginFailCount(String id) {
        redisTemplate.opsForHash().put(HASH_LOGIN_COUNT, id, new LoginFailCountRequest(id, 0));
    }

    public Integer getLoginFailCount(String id) {
        return ((LoginFailCountRequest) Objects.requireNonNull(redisTemplate.opsForHash().get(HASH_LOGIN_COUNT, id))).getFailCount();
    }

    public void incrementLoginFailCount(String id) {
        Integer failCount = getLoginFailCount(id) + 1;

        if (failCount >= 5){
            initializeLoginFailCount(id);
            redisTemplate.opsForHash().put(HASH_LOGIN_LOCK + id, id, id);
            redisTemplate.expire(HASH_LOGIN_LOCK + id, Duration.ofMinutes(5));
        } else {
            redisTemplate.opsForHash().put(HASH_LOGIN_COUNT, id, new LoginFailCountRequest(id, failCount));
        }

    }

    public boolean loginLockMemberCheck(String id) {
        return redisTemplate.opsForHash().hasKey(HASH_LOGIN_LOCK + id, id);
    }
}
