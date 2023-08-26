package com.group.libraryapp.domain.util

data class Slice<T>(
    val hasNext: Boolean,
    val elements: List<T>
) : Collection<T> by elements
