package com.group.libraryapp.domain.book

import com.group.libraryapp.domain.TimeInfoEntity
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.exception.loanFail
import com.group.libraryapp.type.book.BookType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Version

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
            else -> loanFail(this.id)
        }

    fun changeStock(count: Int) {
        this.stock += count
    }
}
