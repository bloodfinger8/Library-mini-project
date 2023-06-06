package com.group.libraryapp.gateway.telegram

interface Notifier {
    fun registered(bookName: String)
    fun loaned(userName: String, bookName: String)
    fun returned(userName: String, bookName: String)
}
