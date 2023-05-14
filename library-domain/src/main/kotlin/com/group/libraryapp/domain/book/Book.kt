package com.group.libraryapp.domain.book

import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.exception.fail
import com.group.libraryapp.exception.loanFail
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class Book(
    val name: String,

    @Enumerated(EnumType.STRING)
    val type: BookType,

    val publisher: String? = null,

    var stock: Int,

    val location: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company? = null,

    @Version
    var version: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    @CreationTimestamp
    lateinit var createdAt: ZonedDateTime

    @UpdateTimestamp
    lateinit var updatedAt: ZonedDateTime

    init {
        if (name.isBlank()) {
            fail()
        }
    }

    companion object {
        fun create(
            name: String = "book name",
            type: BookType = BookType.COMPUTER,
            publisher: String? = null,
            stock: Int = 1,
            location: String? = null,
            company: Company? = null,
            version: Long = 1,
            id: Long? = null
        ): Book {
            return Book(name, type, publisher, stock, location, company, version, id)
        }
    }

    fun canLoanBook(): Boolean =
        when {
            this.stock > 0 -> true
            else -> loanFail(this.id)
        }

    fun changeStock(count: Int) {
        this.stock += count
    }
}
