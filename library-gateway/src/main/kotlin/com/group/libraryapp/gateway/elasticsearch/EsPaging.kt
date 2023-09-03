package com.group.libraryapp.gateway.elasticsearch

class ESPaging(
    private val page: Int,
    private val pageSize: Int
) {
    companion object {
        const val ES_PAGE_MAX = 10_000
    }

    fun getFrom() = page * pageSize

    fun getPageSize() = pageSize

    fun getFetchSize(): Int {
        val last = page * pageSize + pageSize
        if (last < ES_PAGE_MAX) {
            return pageSize + 1
        }
        return Integer.min(pageSize, ES_PAGE_MAX - page * pageSize)
    }
}
