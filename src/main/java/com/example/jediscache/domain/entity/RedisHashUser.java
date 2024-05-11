package com.example.jediscache.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash(value = "redis-hash user", timeToLive = 30L)
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RedisHashUser {
    @Id
    private Long id;

    private String name;

    @Indexed
    private String email;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
