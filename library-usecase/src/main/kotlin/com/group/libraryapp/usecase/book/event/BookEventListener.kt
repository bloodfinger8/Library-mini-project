package com.group.libraryapp.usecase.book.event

import com.group.libraryapp.domain.book.event.BookRegisteredNotifier
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
}
