package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserSignInRequest
import com.group.libraryapp.dto.user.response.UserSignInResponse
import com.group.libraryapp.security.JWTAccessToken
import com.group.libraryapp.security.JWTTokenProvider
import com.group.libraryapp.exception.loginFail
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSignInUseCase(
        val authenticationManager: AuthenticationManager,
        val tokenProvider: JWTTokenProvider,
        val userRepository: UserRepository,
) {
    @Transactional
    fun signIn(request: UserSignInRequest): UserSignInResponse {
        val user = findUser(request)
        emailPasswordAuthenticate(request)
        return UserSignInResponse.of(user, accessToken(user))
    }

    private fun findUser(request: UserSignInRequest) =
        userRepository.findByEmail(Email(request.email)) ?: loginFail()

    private fun emailPasswordAuthenticate(request: UserSignInRequest) {
        SecurityContextHolder.getContext().authentication =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
    }

    private fun accessToken(user: User) =
        tokenProvider.signAcToken(JWTAccessToken.of(user.id!!, user.email, user.name))
}