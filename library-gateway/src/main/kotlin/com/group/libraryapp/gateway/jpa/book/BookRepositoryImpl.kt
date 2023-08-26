package com.group.libraryapp.gateway.jpa.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.util.Slice
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl(
    private val bookJpaAdapter: BookJpaAdapter
) : BookRepository {
    override fun save(book: Book): Book {
        return bookJpaAdapter.save(book)
    }

    override fun saveAll(books: List<Book>) {
        bookJpaAdapter.saveAll(books)
    }

    override fun findById(id: Long): Book? {
        return bookJpaAdapter.findByIdOrNull(id)
    }

    override fun findByName(name: String): Book? {
        return bookJpaAdapter.findByName(name)
    }

    override fun findAllByCompanyId(companyId: Long, page: Int, pageSize: Int): Slice<Book> {
        val result = bookJpaAdapter.findAllByCompanyId(companyId, PageRequest.of(page, pageSize))
        return Slice(result.hasNext(), result.content)
    }
}
