package com.group.libraryapp.dto.book.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BookRequest(
    @field:NotBlank
    val name: String,
    var publisher: String? = null,
    @field:NotNull
    var stock: Int = 1,
    @field:NotNull
    val type: BookType,
    var location: String? = null,
)

enum class BookType {
    COMPUTER,
    ECONOMY,
    SOCIETY,
    LANGUAGE,
    SCIENCE,
}
