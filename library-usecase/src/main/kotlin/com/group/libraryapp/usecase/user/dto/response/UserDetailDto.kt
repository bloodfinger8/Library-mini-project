package com.group.libraryapp.usecase.user.dto.response

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.usecase.group.dto.CompanyDetailDto
import java.time.LocalDateTime

data class UserDetailDto(
    val id: Long,
    val email: String,
    val name: String,
    val companyDto: CompanyDetailDto,
    val bookLoanHistory: List<BookLoanHistoryDto>,
    val nickname: String,
    val introduction: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(user: User): UserDetailDto {
            return UserDetailDto(
                id = user.id!!,
                email = user.email.toString(),
                name = user.name,
                companyDto = CompanyDetailDto.of(user.company!!),
                bookLoanHistory = user.userLoanHistories.map { BookLoanHistoryDto.from(it) },
                nickname = user.name,
                introduction = user.introduction,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
        }
    }
}
