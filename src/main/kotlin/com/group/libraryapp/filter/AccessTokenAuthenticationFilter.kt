package com.group.libraryapp.filter

import com.group.libraryapp.usecase.jwt.JWTTokenService
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AccessTokenAuthenticationFilter(jwtTokenService: JWTTokenService) : OncePerRequestFilter() {

    companion object {
        const val TOKEN_TYPE = "Bearer"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = splitAccessToken(request)

        //token 디코딩 작업

        filterChain.doFilter(request, response);
    }

    private fun splitAccessToken(request: HttpServletRequest): String? =
        request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.startsWith(TOKEN_TYPE) }
            ?.split(" ")
            ?.getOrNull(1)
}