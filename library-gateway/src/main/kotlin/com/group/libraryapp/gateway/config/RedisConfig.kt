package com.group.libraryapp.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@EnableCaching
class RedisConfig(
    @Value("\${spring.redis.host}")
    private val host: String,
    @Value("\${spring.redis.port}")
    private val port: Int
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(host, port)

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
//        val objectMapper = ObjectMapper()
//            .registerModule(KotlinModule())
//            .registerModule(JavaTimeModule())
//            .activateDefaultTyping(
//                BasicPolymorphicTypeValidator.builder()
//                    .allowIfBaseType(Any::class.java)
//                    .build(),
//                ObjectMapper.DefaultTyping.EVERYTHING
//            )
//
//        val serializer = GenericJackson2JsonRedisSerializer(objectMapper)

        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        return redisTemplate
    }
}
