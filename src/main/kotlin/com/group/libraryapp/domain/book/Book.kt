package com.group.libraryapp.domain.book

import com.group.libraryapp.domain.book.type.BookType
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
class Book (
    val name: String,

    @Enumerated(EnumType.STRING)
    val type: BookType,

    val publisher: String? = null,

    var stock: Int,

    @Version
    var version: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
){
    @CreationTimestamp
    lateinit var createdAt: ZonedDateTime
    @UpdateTimestamp
    lateinit var updatedAt: ZonedDateTime

    init {
        if(name.isBlank()) {
            throw IllegalArgumentException("이름은 필수값 입니다.")
        }
    }

    companion object {
        fun create(name: String = "book name",
                   type: BookType = BookType.COMPUTER,
                   publisher: String? = null,
                   stock: Int = 1,
                   version: Long = 1,
                   id: Long? = null
        ): Book {
            return Book(name, type, publisher, stock, version, id)
        }
    }

    fun canLoanBook(): Boolean =
        when {
            this.stock > 0 -> true
            else -> throw IllegalArgumentException("수량 부족")
        }

    fun changeStock(count: Int) {
        this.stock += count
    }
}