package com.group.libraryapp.domain.book

import com.group.libraryapp.domain.book.type.BookType
import javax.persistence.*

@Entity
class Book (
    val name: String,

    @Enumerated(EnumType.STRING)
    val type: BookType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
){
    init {
        if(name.isBlank()) {
            throw IllegalArgumentException("이름은 필수값 입니다.")
        }
    }

    companion object {
        fun create(name: String = "book name",
                   type: BookType = BookType.COMPUTER,
                   id: Long? = null
        ): Book {
            return Book(name, type, id)
        }
    }

}