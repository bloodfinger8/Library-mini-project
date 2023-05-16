package com.group.libraryapp.dto.book.response

import com.group.libraryapp.domain.company.Company

data class CompanyDto(
    val id: Long,
    val name: String
) {
    companion object {
        fun of(company: Company): CompanyDto {
            return CompanyDto(company.id!!, company.name)
        }
    }
}
