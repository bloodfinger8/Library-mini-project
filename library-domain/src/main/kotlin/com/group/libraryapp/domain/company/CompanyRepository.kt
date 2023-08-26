package com.group.libraryapp.domain.company

import com.group.libraryapp.domain.util.Slice

interface CompanyRepository {
    fun save(company: Company): Company
    fun findById(id: Long): Company?
    fun findAll(page: Int, pageSize: Int): Slice<Company>
}
