package com.group.libraryapp.domain.book

import com.group.libraryapp.domain.TimeInfoEntity
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.exception.bookLoanFail
import com.group.libraryapp.type.book.BookType
import jakarta.persistence.*

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
) : TimeInfoEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Book
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    fun canLoanBook(): Boolean =
        when {
            this.stock > 0 -> true
            else -> bookLoanFail(this.id)
        }

    fun addStock() {
        this.stock = this.stock + 1
    }

    fun removeStock() {
        this.stock = this.stock - 1
    }

    fun getMyType(): String = this.type.name
}
