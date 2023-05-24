package com.group.libraryapp

data class SliceDto<T>(
    val hasNext: Boolean = false,
    val elements: List<T> = emptyList()
) {
    companion object {
        fun <T> empty() = SliceDto<T>(false, listOf())
    }
}

data class TotalSliceDto<T>(
    val hasNext: Boolean = false,
    val total: Long = 0,
    val elements: List<T> = emptyList()
) {
    companion object {
        fun <T> empty() = TotalSliceDto<T>(false, 0, emptyList())
    }
}
