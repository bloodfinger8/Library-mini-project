package com.group.libraryapp.usecase.group.dto

import com.group.libraryapp.domain.company.Company
import java.time.LocalDateTime

data class CompanyDetailDto(
    val id: Long,
    val name: String,
    val domain: String,
    val numberOfEmployee: Int,
    val status: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(company: Company): CompanyDetailDto {
            return CompanyDetailDto(
                id = company.id!!,
                name = company.name,
                domain = company.domain,
                numberOfEmployee = company.numberOfEmployee,
                status = company.status.name,
                createdAt = company.createdAt
            )
        }
    }

}
