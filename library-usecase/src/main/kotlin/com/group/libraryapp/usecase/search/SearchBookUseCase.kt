package com.group.libraryapp.usecase.search

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchBookUseCase {

    @Transactional(readOnly = true)
    fun get() {
    }
}
