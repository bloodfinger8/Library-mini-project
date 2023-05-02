package com.group.libraryapp.dto.book.response

import com.group.libraryapp.domain.company.Company
import org.springframework.data.domain.Page

data class ListCompanyResponse (
    val company : List<CompanyInfo>,
    val hasNext: Boolean,
){
    companion object {
        fun of(company : Page<Company>): ListCompanyResponse {
            return ListCompanyResponse(company.map { CompanyInfo.of(it) }.toList(), company.hasNext())
        }
    }

    class CompanyInfo (
        val id: Long,
        val name: String,
    ){
        companion object {
            fun of(company: Company): CompanyInfo {
                return CompanyInfo(company.id!!,company.name)
            }
        }

    }
}