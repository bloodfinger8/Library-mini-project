package com.group.libraryapp.gateway.jpa.company

import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.util.Slice
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CompanyRepositoryImpl(
    private val adapter: CompanyJpaAdapter
) : CompanyRepository {
    override fun save(company: Company): Company {
        return adapter.save(company)
    }

    override fun findById(id: Long): Company? {
        return adapter.findByIdOrNull(id)
    }

    override fun findAll(page: Int, pageSize: Int): Slice<Company> {
        val result = adapter.findAll(PageRequest.of(page, pageSize))
        return Slice(result.hasNext(), result.content)
    }
}
