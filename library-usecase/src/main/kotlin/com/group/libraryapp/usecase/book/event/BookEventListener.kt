package com.group.libraryapp.usecase.book.event

import com.group.libraryapp.domain.book.event.BookLoanedNotifier
import com.group.libraryapp.domain.book.event.BookRegisteredNotifier
import com.group.libraryapp.domain.book.event.BookReturnedNotifier
import com.group.libraryapp.gateway.telegram.Notifier
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class BookEventListener(
    private val notifier: Notifier
) {

    @Async
    @TransactionalEventListener
    fun bookRegistered(event: BookRegisteredNotifier) {
        notifier.registered(event.book.name)
    }

    @Async
    @TransactionalEventListener
    fun bookLoaned(event: BookLoanedNotifier) {
        notifier.loaned(event.userName, event.bookName)
    }

    @Async
    @TransactionalEventListener
    fun bookReturned(event: BookReturnedNotifier) {
        notifier.returned(event.userName, event.bookName)
    }
}
