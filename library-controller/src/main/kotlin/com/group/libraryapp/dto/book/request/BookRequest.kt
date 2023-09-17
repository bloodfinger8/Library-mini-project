package com.group.libraryapp.dto.book.request

import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.type.book.BookType
import com.group.libraryapp.usecase.book.dto.command.RegisterBookCommand
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BookRequest(
    @field:NotBlank
    val name: String,
    var publisher: String? = null,
    @field:NotNull
    var stock: Int = 1,
    @field:NotNull
    val type: BookType,
    var location: String? = null
) {

    fun toCmd(auth: AuthenticationDTO): RegisterBookCommand {
        return RegisterBookCommand(
            name,
            publisher,
            stock,
            type.name,
            location,
            auth.companyId
        )
    }
}
