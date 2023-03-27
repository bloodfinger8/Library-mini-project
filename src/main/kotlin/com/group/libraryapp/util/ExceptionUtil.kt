package com.group.libraryapp.util

fun fail(message: String = "request value error"): Nothing {
    throw IllegalArgumentException(message)
}