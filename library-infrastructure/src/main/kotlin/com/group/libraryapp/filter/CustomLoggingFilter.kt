package com.group.libraryapp.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.AbstractRequestLoggingFilter

class CustomLoggingFilter(
    private val ignorePaths: Set<String> = emptySet(),
    private val sensitivePaths: Set<String> = emptySet()
) : AbstractRequestLoggingFilter() {
    init {
        setAfterMessagePrefix("")
        setAfterMessageSuffix("")
    }

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return when {
            ignorePaths.contains(request.requestURI) -> false
            sensitivePaths.contains(request.requestURI) -> {
                request.setAttribute(SENSITIVE, true)
                true
            }
            else -> true
        }
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        request.setAttribute(REQUESTED_AT, System.currentTimeMillis())
        logger.debug(message)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.setAttribute(RESPONSE_OBJ, response)
        super.doFilterInternal(request, response, filterChain)
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        val requestedAt = request.getAttribute(REQUESTED_AT)
        val res = request.getAttribute(RESPONSE_OBJ) as HttpServletResponse
        if (requestedAt == null) {
            logger.debug(message)
            return
        }
        var prefixTag = ""
        val elapsed = (System.currentTimeMillis() - requestedAt as Long) / 1000.0
        if (elapsed > 1) {
            prefixTag = SLOW_TAG
        }
        logger.info("After request [${prefixTag}method=${request.method}, $message elapsed=$elapsed, status=${res.status}]")
    }

    override fun getMessagePayload(request: HttpServletRequest): String? {
        if (request.getAttribute(SENSITIVE) == true) return null
        return super.getMessagePayload(request)
    }

    companion object {
        private const val RESPONSE_OBJ = "LOGGING -> APPLICATION_RESPONSE"
        private const val REQUESTED_AT = "LOGGING -> REQUESTED_AT"
        private const val SLOW_TAG = "LOGGING -> SLOW_API"
        private const val SENSITIVE = "LOGGING -> APPLICATION_SENSITIVE"
    }
}
