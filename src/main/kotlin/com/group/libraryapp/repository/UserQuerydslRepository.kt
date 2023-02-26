package com.group.libraryapp.repository

import com.group.libraryapp.domain.user.QUser.user
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.QUserLoanHistory.userLoanHistory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserQuerydslRepository (
    private val queryFactory: JPAQueryFactory
){

    fun getHistories(): List<User>{
        return queryFactory.select(user).distinct()
            .from(user)
            .leftJoin(userLoanHistory).on(userLoanHistory.user.id.eq(user.id))
            .fetchJoin()
            .fetch()
    }
}