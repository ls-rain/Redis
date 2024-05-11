package com.example.jediscache.service;

import com.example.jediscache.domain.entity.RedisHashUser;
import com.example.jediscache.domain.entity.User;
import com.example.jediscache.domain.repository.RedisHashUserRepository;
import com.example.jediscache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;
    private final RedisHashUserRepository redisHashUserRepository;
    public User getUser(final Long id){
        String key = "users:%d".formatted(id);
        //1.cache에서 가져옴
        Object cachedUser = objectRedisTemplate.opsForValue().get(key);
        if(cachedUser != null){
            return (User)cachedUser;
        }
        //cache에 없으면
        //2.db에서 응답하고, cache에 set
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
        return user;
    }

    public RedisHashUser getUser2(final Long id){
        RedisHashUser cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        });
        return cachedUser;

//        String key = "users:%d".formatted(id);
//        //1.cache에서 가져옴
//        Object cachedUser = objectRedisTemplate.opsForValue().get(key);
//        if(cachedUser != null){
//            return (User)cachedUser;
//        }
//        //cache에 없으면
//        //2.db에서 응답하고, cache에 set
//        User user = userRepository.findById(id).orElseThrow();
//        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
//        return user;
    }
}
