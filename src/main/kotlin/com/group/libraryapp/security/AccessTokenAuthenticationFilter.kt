package com.group.libraryapp.security

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.UserType
import com.group.libraryapp.util.UserRole
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AccessTokenAuthenticationFilter(
    val jwtTokenProvider: JWTTokenProvider
) : OncePerRequestFilter() {

    companion object {
        const val TOKEN_TYPE = "Bearer"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = splitAccessToken(request)

        if(token != null) {
            val jwtAcToken = jwtTokenProvider.accessTokenFromSigned(token)
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    AuthenticationDTO(jwtAcToken.get("email") as String, jwtAcToken.get("name") as String, UserType.valueOf(jwtAcToken.get("userType") as String)),
                    token,
                    AuthorityUtils.createAuthorityList(UserRole.ROLE_USER)
                )
        }

        filterChain.doFilter(request, response);
    }

    private fun splitAccessToken(request: HttpServletRequest): String? =
        request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.startsWith(TOKEN_TYPE) }
            ?.split(" ")
            ?.getOrNull(1)
}