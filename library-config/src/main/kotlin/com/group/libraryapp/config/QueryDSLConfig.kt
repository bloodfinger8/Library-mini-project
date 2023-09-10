package com.group.libraryapp.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDSLConfig(
    private val entityManager: EntityManager
) {
    @Bean
    fun queryDSL(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}
